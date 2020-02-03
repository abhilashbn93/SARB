package sarb.core.services;

/**
 * The Interface ApiEndpointService.
 */
public interface ApiEndpointService {
    
    /**
     * Gets the timeout config.
     *
     * @return the timeout config
     */
    public int getRequestTimeoutConfig();

    /**
     * Gets the socket timeout config.
     *
     * @return the socket timeout config
     */
    public int getSocketTimeoutConfig();

    /**
     * Gets the connect timeout config.
     *
     * @return the connect timeout config
     */
    public int getConnectTimeoutConfig();

}
