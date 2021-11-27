package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
    public static String parse(MultipartFile file) {
        log.trace("NativeParserUtil : parse, file=[{}]", file);
        // TODO: a beérkező file egy caff vagy ciff fájl
        //  erre kell meghívni a natív parsert ebben a függvényben
        //  generáljunk egy uuid-t nekik és azon a néven mentsük a gifet és a caffot is
        //  a generált uuid-t pedig visszatérési értékként adjuk meg ebben a függvényben
        //  a parser generál egy jsont is, ebből bányásszuk ki a metaadatokat amiket mentünk az Image entitásba
        //  utána lehetőleg töröljük a jsont
        return UUID.randomUUID().toString();
    }
}
