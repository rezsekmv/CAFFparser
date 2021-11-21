package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtil {

    /**
     * Visszaadja az aktuális bejelentkezett felhasználó azonosítóját
     *
     * @return bejelentkezett felhasználó azonosítója
     */
    public static Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
