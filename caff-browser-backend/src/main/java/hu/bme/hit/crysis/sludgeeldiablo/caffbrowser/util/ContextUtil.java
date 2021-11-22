package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtil {

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String ADMIN = "admin";

    /**
     * Visszaadja az aktuális bejelentkezett felhasználó felhasználónevét
     *
     * @return bejelentkezett felhasználó felhasználónevét
     */
    public static String getCurrentUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ANONYMOUS_USER.equals(username) ? null : username;
    }
}
