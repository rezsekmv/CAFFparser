package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
public class NativeParserUtil {

    private static final String GIF_PATH = "dummy/{uuid}.gif";
    private static final String JSON_PATH = "dummy/{uuid}.json";

    public static String getGifPath(String uuid) {
        return GIF_PATH.replace("{uuid}", uuid);
    }

    public static String getJsonPath(String uuid) {
        return JSON_PATH.replace("{uuid}", uuid);
    }

    public static String parse(MultipartFile file) {
        log.trace("NativeParserUtil : parse, file=[{}]", file);
        // TODO: a beérkező file egy caff vagy ciff fájl
        //  erre kell meghívni a natív parsert ebben a függvényben
        //  generáljunk egy uuid-t nekik és azon a néven mentsük a gifet és a jsont is
        //  a generált uuid-t pedig visszatérési értékként adjuk meg ebben a függvényben
        return UUID.randomUUID().toString();
    }
}
