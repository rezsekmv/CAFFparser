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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.PpmReader.ppm;

@Slf4j
public class NativeParserUtil {

    private static final String GIF_PATH = "dummy/{uuid}.gif";
    private static final String CAFF_PATH = "dummy/{uuid}.caff";

    public static String getGifPath(String uuid) {
        return GIF_PATH.replace("{uuid}", uuid);
    }

    public static String getCaffPath(String uuid) {
        return CAFF_PATH.replace("{uuid}", uuid);
    }

    /**
     * A fogadott CIFF vagy CAFF fájlt GIF fájlként, illetve kiegészítő JSON adatfájlként
     * menti a fájlrendszerre egy helyben generált UUID alapján, melyet továbbít az alkalmazásnak
     *
     * @param file feltöltött CIFF vagy CAFF fájl
     * @return generált UUID azonosító
     */
    public static Image parse(MultipartFile file, String path) throws IOException {
        log.trace("NativeParserUtil : parse, file=[{}]", file);
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".caff")) {
            throw new CbNativeParserException("The image is not a .caff file");
        }

        //TODO parser
        String imageName = saveCaff(file, path);
        CaffJson caff = parseJson(imageName, path);
        createGif(caff, imageName, path);
        return createImageModel(imageName, caff);
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
                caff.getAnimation().getCIFFs()
                        .stream()
                        .map(Ciff::getCaption)
                        .collect(Collectors.toSet())
        );
        image.setTags(
                caff.getAnimation().getCIFFs()
                        .stream()
                        .map(Ciff::getTags)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        );
        image.setHeight(caff.getAnimation().getCIFFs().get(0).getHeight());
        image.setWidth(caff.getAnimation().getCIFFs().get(0).getWidth());

        return image;
    }

    private static String saveCaff(MultipartFile file, String path) throws IOException {
        String imageName = UUID.randomUUID().toString();
        file.transferTo(new File(path + "\\caff-browser-backend\\src\\main\\resources\\static\\" + imageName + ".caff"));
        return imageName;
    }

    private static CaffJson parseJson(String filename, String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path + "\\caff-browser-native-parser/output-json/" + filename + ".caff-json.json")));

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<CaffJson> jsonAdapter = moshi.adapter(CaffJson.class);

        CaffJson caff = jsonAdapter.fromJson(content);
        System.out.println(caff);
        return caff;
    }

    private static void createGif(CaffJson caff, String filename, String path) throws IOException {
        File dir = new File(path + "\\caff-browser-native-parser\\output-images");
        List<File> images = Arrays.stream(Objects.requireNonNull(dir.listFiles())).filter(file -> file.getName().startsWith(filename)).collect(Collectors.toList());

        ImageOutputStream output = new FileImageOutputStream(new File(path + "\\caff-browser-backend\\src\\main\\resources\\static\\" + filename + ".gif"));
        GifSequenceWriter writer = new GifSequenceWriter(output, 1, 1, true);

        int width = (int) caff.getAnimation().getCIFFs().get(0).getWidth();
        int height = (int) caff.getAnimation().getCIFFs().get(0).getHeight();
        for (File image : images) {
            byte[] fileContent = Files.readAllBytes(image.toPath());
            BufferedImage bufferedImage = ppm(width, height, 255, Arrays.copyOfRange(fileContent, 4, fileContent.length - 1));
            writer.writeToSequence(bufferedImage);
        }

        writer.close();
        output.close();
    }
}
