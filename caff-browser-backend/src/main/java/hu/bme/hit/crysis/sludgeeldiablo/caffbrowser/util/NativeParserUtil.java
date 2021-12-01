package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CaffJson;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.Ciff;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.Credit;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNativeParserException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.GifSequenceWriter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.PpmReader.ppm;

@Slf4j
public class NativeParserUtil {

    private static final String GIF_PATH = "/{uuid}.gif";
    private static final String CAFF_PATH = "/{uuid}.caff";
    private static final String JSON_PATH = "/{uuid}.caff-json.json";

    private static final String SERVER_IMAGES_PATH = "/caff-browser-backend/src/main/resources/static";
    private static final String PARSER_OUTPUT_JSON_PATH = "/caff-browser-native-parser/output-json";
    private static final String PARSER_OUTPUT_IMAGES_PATH = "/caff-browser-native-parser/output-images";

    private static final String REPOSITORY_PATH = getRepositoryPath();

    public static String getGifPath(String uuid) {
        return GIF_PATH.replace("{uuid}", uuid);
    }

    public static String getCaffPath(String uuid) {
        return CAFF_PATH.replace("{uuid}", uuid);
    }

    private static String getJsonPath(String uuid) {
        return JSON_PATH.replace("{uuid}", uuid);
    }

    private static String getRepositoryPath() {
        return FileSystems.getDefault().getPath("").normalize().toAbsolutePath().toString()
                .replaceAll("\\\\", "/")
                .replace("/caff-browser-backend", "");
    }

    /**
     * A fogadott CIFF vagy CAFF fájlt GIF fájlként, illetve kiegészítő JSON adatfájlként
     * menti a fájlrendszerre egy helyben generált UUID alapján, melyet továbbít az alkalmazásnak
     *
     * @param file feltöltött CIFF vagy CAFF fájl
     * @return generált UUID azonosító
     */
    public static Image parse(MultipartFile file) throws IOException {
        log.trace("NativeParserUtil : parse, file=[{}]", file);
        validateFormat(file);

        // TODO: parser meghívása, hogy legenerálja a ppm-eket amiket felhasználunk a következő sorokban

        String uuid = saveCaff(file);
        CaffJson caffJson = parseJson(uuid);
        createGif(caffJson, uuid);

        return createImageModel(uuid, caffJson);
    }

    private static void validateFormat(MultipartFile file) {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".caff")) {
            throw new CbNativeParserException("The image is not a .caff file");
        }
    }

    private static String saveCaff(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        file.transferTo(new File(REPOSITORY_PATH + SERVER_IMAGES_PATH + getCaffPath(uuid)));
        return uuid;
    }

    private static CaffJson parseJson(String uuid) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(REPOSITORY_PATH + PARSER_OUTPUT_JSON_PATH + getJsonPath(uuid))));

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<CaffJson> jsonAdapter = moshi.adapter(CaffJson.class);

        return jsonAdapter.fromJson(content);
    }

    private static void createGif(CaffJson caffJson, String uuid) throws IOException {
        ImageOutputStream output = new FileImageOutputStream(new File(REPOSITORY_PATH + SERVER_IMAGES_PATH + getGifPath(uuid)));
        GifSequenceWriter writer = new GifSequenceWriter(output, 1, 1, true);

        int width = (int) caffJson.getAnimation().getCiffs().get(0).getWidth();
        int height = (int) caffJson.getAnimation().getCiffs().get(0).getHeight();
        for (File image : getGifParts(uuid)) {
            byte[] fileContent = Files.readAllBytes(image.toPath());
            BufferedImage bufferedImage = ppm(width, height, 255, Arrays.copyOfRange(fileContent, 4, fileContent.length - 1));
            writer.writeToSequence(bufferedImage);
        }

        writer.close();
        output.close();
    }

    private static List<File> getGifParts(String uuid) {
        File dir = new File(REPOSITORY_PATH + PARSER_OUTPUT_IMAGES_PATH);
        return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> file.getName().startsWith(uuid))
                .collect(Collectors.toList());
    }

    private static Image createImageModel(String imageName, CaffJson caff) {
        Image image = new Image();

        Credit credit = caff.getCredit();
        int year = (int) credit.getYear();
        int month = (int) credit.getMonth();
        int day = (int) credit.getDay();
        int hour = (int) credit.getHour();
        int minute = (int) credit.getMinute();

        image.setUuid(imageName);
        image.setDate(LocalDateTime.of(year, month, day, hour, minute));
        image.setCredit(credit.getCreator());
        image.setCaptions(
                caff.getAnimation().getCiffs()
                        .stream()
                        .map(Ciff::getCaption)
                        .collect(Collectors.toSet())
        );
        image.setTags(
                caff.getAnimation().getCiffs()
                        .stream()
                        .map(Ciff::getTags)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        );
        image.setHeight(caff.getAnimation().getCiffs().get(0).getHeight());
        image.setWidth(caff.getAnimation().getCiffs().get(0).getWidth());

        return image;
    }
}
