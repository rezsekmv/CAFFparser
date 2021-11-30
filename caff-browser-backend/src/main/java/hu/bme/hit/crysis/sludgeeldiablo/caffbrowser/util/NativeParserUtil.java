package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CaffJson;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
    public static String parse(MultipartFile file, String path) {
        log.trace("NativeParserUtil : parse, file=[{}]", file);
        // TODO: a beérkező file egy caff vagy ciff fájl
        //  erre kell meghívni a natív parsert ebben a függvényben
        //  generáljunk egy uuid-t nekik és azon a néven mentsük a gifet és a caffot is
        //  a generált uuid-t pedig visszatérési értékként adjuk meg ebben a függvényben
        //  a parser generál egy jsont is, ebből bányásszuk ki a metaadatokat amiket mentünk az Image entitásba
        //  utána lehetőleg töröljük a jsont

        CaffJson caff = parseJson(file.getOriginalFilename(), path);
        createGif(caff, file.getOriginalFilename(), path);

        return UUID.randomUUID().toString();
    }

    private static CaffJson parseJson(String filename, String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path + "caff-browser-native-parser/output-json/" + filename + "-json.json")));

            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<CaffJson> jsonAdapter = moshi.adapter(CaffJson.class);

            CaffJson caff = jsonAdapter.fromJson(content);
            System.out.println(caff);
            return caff;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createGif(CaffJson caff, String filename, String path) {
        try {
            File dir = new File(path + "\\caff-browser-native-parser\\output-images");
            List<File> images = Arrays.stream(Objects.requireNonNull(dir.listFiles())).filter(file -> file.getName().startsWith(filename)).collect(Collectors.toList());

            ImageOutputStream output = new FileImageOutputStream(new File(path + "\\caff-browser-backend\\src\\main\\resources\\static\\" + filename + ".gif"));
            GifSequenceWriter writer = new GifSequenceWriter(output, 1, 1, true);

            for (File image : images) {
                byte[] fileContent = Files.readAllBytes(image.toPath());
                int width = caff.getAnimation().getCIFFs().get(0).getWidth().intValue();
                int height = caff.getAnimation().getCIFFs().get(0).getHeight().intValue();
                BufferedImage bufferedImage = ppm(width, height, 255, Arrays.copyOfRange(fileContent, 4, fileContent.length - 1));
                writer.writeToSequence(bufferedImage);
            }

            writer.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
