package sarb.api.client;

import sarb.api.request.BaseRequest;
import sarb.api.response.BaseResponse;

import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * The Interface RestClient.
 */
public interface RestClient {

    /**
     * Send request with body string.
     *
     * @param endpoint       the endpoint
     * @param headerMap      the header map
     * @param bodyJsonObject the body json object
     * @return the string
     */
    BaseResponse sendRequest(final BaseRequest request, final Map<String, String> headerMap);
    
    CloseableHttpClient getHttpClient();

}
