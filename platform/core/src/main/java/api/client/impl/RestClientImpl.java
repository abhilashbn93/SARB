package sarb.api.client.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sarb.api.client.RestClient;
import sarb.api.request.BaseRequest;
import sarb.api.response.BaseResponse;
import sarb.api.response.impl.BaseResponseImpl;
import sarb.core.services.ApiEndpointService;
import sarb.core.utils.TimeLoggingUtil;

/**
 * The Class RestServiceImpl. The Rest Client uses
 * PoolingHttpClientConnectionManager to create the ClosableHttpClient THe
 * Connection is set with max connections with 200
 */
@Component(service = RestClient.class)
public class RestClientImpl implements RestClient {
    /** The Constant P_GZIP. */
  
	/**
     * The Constant LOG.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(RestClientImpl.class);

    /** The http client. */
    private CloseableHttpClient httpClient;

    /** The cm. */
    private PoolingHttpClientConnectionManager cm;
    
    /** The api endpoint service. */
    @Reference
    public ApiEndpointService apiEndpointService;

    
    @Override
    public final BaseResponse sendRequest(final BaseRequest request,
                                    final Map<String, String> headerMap) {

        long lStartTime = TimeLoggingUtil.getStartTime();
        LOG.debug( "**************************** Start Request ********************* " );
        LOG.debug("API URL -  {}", request.getUrl());
        LOG.trace("Request data {}", request.getData());
        BaseResponse baseResponse = null;
        
        StringEntity requestEntity =
                new StringEntity(request.getData(), ContentType.APPLICATION_JSON);
        //Redirect to Respective HTTP Method based on Request Type
        switch(request.gethttpMethodType().toString()){
        	case HttpConstants.METHOD_POST:
        		baseResponse = setPostRequest(request, requestEntity, headerMap);
        		break;
        	case HttpConstants.METHOD_GET :
        		baseResponse = setGetRequest(request, headerMap);
        		break;
        	case HttpConstants.METHOD_PUT :
        		requestEntity = new StringEntity(request.getData(), ContentType.APPLICATION_OCTET_STREAM);
        		baseResponse = setPutRequest(request, requestEntity, headerMap);
        		break;        	
            default :
            	baseResponse = setGetRequest(request, headerMap);
            	break;
        }
       
        LOG.debug("Total Time Taken for API Request - {} ms", TimeLoggingUtil.logMethodTime( lStartTime ) );
        LOG.debug("**************************** End Request *********************");
        
        LOG.trace("Exit sendRequestWithBody");
        return baseResponse;
    }

	/**
	 * @param request
	 * @param requestEntity
	 * @param headerMap
	 * @return
	 */
	private BaseResponse setPostRequest(final BaseRequest request, StringEntity requestEntity,
			final Map<String, String> headerMap) {
		HttpPost postMethod = null;
		HttpResponse apiResponse = null;
		BaseResponse baseResponse = null;
		try {
		postMethod = new HttpPost(request.getUrl());       
        postMethod.setEntity(requestEntity);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            	LOG.error("Header key is {} and value is {} ",entry.getKey(),entry.getValue());
            	postMethod.addHeader(entry.getKey(), entry.getValue());
            }
        }
        
        apiResponse = httpClient.execute(postMethod);
        baseResponse = new BaseResponseImpl(apiResponse);
		} catch (IOException e) {
            LOG.error("IOException occurred in Rest Client in HTTP Post Method {} ",e.getMessage());
        } 
        finally {
            if (null != postMethod) {
                postMethod.releaseConnection();
            }           
        }
        return baseResponse;
	}
	
	/**
	 * @param request
	 * @param headerMap
	 * @return
	 */
	private BaseResponse setGetRequest(final BaseRequest request, final Map<String, String> headerMap) {
		HttpGet getMethod = null;
		HttpResponse apiResponse = null;
		BaseResponse baseResponse = null;
		try {
		getMethod = new HttpGet(request.getUrl());
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            	getMethod.addHeader(entry.getKey(), entry.getValue());
            }
        }
        
        apiResponse = httpClient.execute(getMethod);
        baseResponse = new BaseResponseImpl(apiResponse);
		} catch (IOException e) {
            LOG.error("IOException occurred in Rest Client in HTTP Get Method");
        } 
        finally {
            if (null != getMethod) {
            	getMethod.releaseConnection();
            }           
        }
        return baseResponse;
	}
	
	/**
	 * @param request
	 * @param requestEntity
	 * @param headerMap
	 * @return
	 */
	private BaseResponse setPutRequest(final BaseRequest request, StringEntity requestEntity,
			final Map<String, String> headerMap) {
		HttpPut putMethod = null;
		HttpResponse apiResponse = null;
		BaseResponse baseResponse = null;
		try {
		putMethod = new HttpPut(request.getUrl());       
        putMethod.setEntity(requestEntity);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            	putMethod.addHeader(entry.getKey(), entry.getValue());
            }
        }
        
        apiResponse = httpClient.execute(putMethod);
        baseResponse = new BaseResponseImpl(apiResponse);
		} catch (IOException e) {
            LOG.error("IOException occurred in Rest Client in HTTP Put Method");
        } 
        finally {
            if (null != putMethod) {
            	putMethod.releaseConnection();
            }           
        }
        return baseResponse;
	}

	/**
     * Activate.
     */
    @Activate
    protected void activate() {
        LOG.info("RestClientImpl Activated");
        int requestTimeoutConfig = apiEndpointService.getRequestTimeoutConfig();
        int scketTimeoutConfig = apiEndpointService.getSocketTimeoutConfig();
        int connectTimeoutConfig = apiEndpointService.getConnectTimeoutConfig();
        try {
        	SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(new TrustStrategy() {
						@Override
						public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
								throws CertificateException {
							return true;
						}
					}).build();
        	
        	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1.2" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
			// NOSONAR
			cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(500);
            // Increase default max connection per route to 20
            cm.setDefaultMaxPerRoute(100);
            RequestConfig requestConfig =
                    RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                            .setSocketTimeout(scketTimeoutConfig * 1000).setConnectTimeout(connectTimeoutConfig * 1000)
                            .setConnectionRequestTimeout(requestTimeoutConfig * 1000).build();


            // Build the client.
            httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig).setConnectionManager(cm)
                    .build();

        } catch (NoSuchAlgorithmException e) {
            LOG.error("NoSuchAlgorithmException during creation of httpClient");
        } catch (KeyStoreException e) {
            LOG.error("KeyStoreException during creation of httpClient");
        } catch (KeyManagementException e) {
            LOG.error("KeyManagementException during creation of httpClient");
        }
        LOG.info("Exit RestClientImpl Activate");
    }

    /**
     * Deactivate.
     */
    @Deactivate
    protected void deactivate() {
        LOG.info("RestClientImpl Deactivated");
        try {
            if (null != httpClient) {
                httpClient.close();
            }
            if (null != cm) {
                cm.close();
            }
        } catch (IOException e) {
            LOG.error("IOException during closing of httpClient");
        }
    }

    /**
     * Sets the api endpoint service.
     *
     * @param apiEndpointService the new api endpoint service
     */
    public void setApiEndpointService( ApiEndpointService endpointService ) {
        this.apiEndpointService = endpointService;
    }
    
    public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
}
