package sarb.api.request.util;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum RequestMethods.
 * This enum type holds the mapping to all PMA Endpoints.
 * These mapping references are used in all Sling Services which are
 * invoked from JavaScript AJAX call.
 */
public enum RequestMethods {

    WCS_HTTP_PROTOCOL_HOST("httpProtocolHost"),

    WCS_HTTPS_PROTOCOL_HOST("httpsProtocolHost"),

    SESSION_TIMEOUT("sessionTimeout");


    /**
     * The Constant LOOK_UP.
     */
    private static final Map<String, RequestMethods> LOOK_UP = new HashMap<String, RequestMethods>();

    static {
        for (RequestMethods d : RequestMethods.values()) {
            LOOK_UP.put(d.getServiceName(), d);
        }
    }

    /**
     * The service name.
     */
    private final String serviceName;

    /**
     * Instantiates a new service enum.
     *
     * @param serviceName the service name
     */
    private RequestMethods(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets the enum.
     *
     * @param serviceName the service name
     * @return the enum
     */
    public static RequestMethods getEnum(final String serviceName) {
        return LOOK_UP.get(serviceName);
    }

    /**
     * Gets the service name.
     *
     * @return the service name
     */
    public String getServiceName() {
        return serviceName;
    }
}
