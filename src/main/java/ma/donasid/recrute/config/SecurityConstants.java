package ma.donasid.recrute.config;



public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/*";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET ="OurSecretKeyJWS00000KeyJWS00000KeyJWS00000KeyJWS00000";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 3000_000; //300S
}


