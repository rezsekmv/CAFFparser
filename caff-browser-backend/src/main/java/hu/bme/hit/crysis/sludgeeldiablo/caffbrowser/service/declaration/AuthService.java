package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    /**
     * Frissíti az access tokent a refresh token alapján
     *
     * @param request a HTTP request
     * @param response a HTTP response
     */
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
