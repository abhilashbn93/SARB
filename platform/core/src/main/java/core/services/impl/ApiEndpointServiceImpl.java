package sarb.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import sarb.core.services.ApiEndpointService;

/**
 * The Class ApiEndpointServiceImpl.
 */
@Component(immediate = true, service = ApiEndpointService.class)
@Designate(ocd = ApiEndpointServiceImpl.Configuration.class)
public class ApiEndpointServiceImpl implements ApiEndpointService {

	/** The request timeout config. */
	private int requestTimeoutConfig;

	/** The socket timeout config. */
	private int socketTimeoutConfig;

	/** The connect timeout config. */
	private int connectTimeoutConfig;

	/**
	 * Activate.
	 * 
	 * @param config
	 *            the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {

		this.requestTimeoutConfig = Integer.valueOf(config
				.requestTimeoutConfig());
		this.socketTimeoutConfig = Integer
				.valueOf(config.socketTimeoutConfig());
		this.connectTimeoutConfig = Integer.valueOf(config
				.connectTimeoutConfig());
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "API Configuration")
	public @interface Configuration {

		/**
		 * Request timeout config.
		 * 
		 * @return the string
		 */
		@AttributeDefinition(name = "Request Timeout config for API calls", description = "Request Timeout config for API call", type = AttributeType.STRING)
		String requestTimeoutConfig() default "30";

		/**
		 * Socket timeout config.
		 * 
		 * @return the string
		 */
		@AttributeDefinition(name = "Socket Timeout config for API calls", description = "Socket Timeout config for API call", type = AttributeType.STRING)
		String socketTimeoutConfig() default "30";

		/**
		 * Socket timeout config.
		 * 
		 * @return the string
		 */
		@AttributeDefinition(name = "Socket Timeout config for API calls", description = "Socket Timeout config for API call", type = AttributeType.STRING)
		String connectTimeoutConfig() default "30";
		
	}

	public int getRequestTimeoutConfig() {

		return requestTimeoutConfig;
	}

	public int getSocketTimeoutConfig() {

		return socketTimeoutConfig;
	}

	public int getConnectTimeoutConfig() {

		return connectTimeoutConfig;
	}

}
