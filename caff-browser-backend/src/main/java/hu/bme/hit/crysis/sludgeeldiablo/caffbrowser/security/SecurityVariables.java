package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security;

public class SecurityVariables {

    public static final String LOGIN_URL = "/api/login";
    public static final String REFRESH_TOKEN_URL = "/api/refresh-token";
    public static final String PUBLIC_URL = "/api/public/**";
    public static final String ADMIN_URL = "/api/admin/**";
    public static final String MOD_URL = "/api/mod/**";

    public static final String ACCESS_TOKEN_PARAMETER = "accessToken";
    public static final String REFRESH_TOKEN_PARAMETER = "refreshToken";
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String ROLES_PARAMETER = "roles";

    public static final String BEARER_TOKEN_START = "Bearer ";
}
