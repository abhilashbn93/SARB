package sarb.api.response.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sarb.api.response.BaseResponse;

public class BaseResponseImpl implements BaseResponse {

	private int statusCode;
	private String responseData;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseResponseImpl.class);

	public BaseResponseImpl(HttpResponse httpResponse) {
		setStatusCode(httpResponse);
		setResponseData(httpResponse);
	}

	public void setStatusCode(HttpResponse httpResponse) {

		if (httpResponse != null) {
			statusCode = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("******** getStatusCode status code - {}", statusCode);
		}
	}

	public void setResponseData(HttpResponse httpResponse) {
		
		if (httpResponse != null) {
			int statCode = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("******** getResponseData status code - {}",
					statCode);
			if (statCode != SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR
					&& statCode != SlingHttpServletResponse.SC_NOT_FOUND) {
				try {
					if(null != httpResponse.getEntity())
					{
						
						responseData = EntityUtils.toString(httpResponse
							.getEntity());
					}
					else
						responseData = StringUtils.EMPTY;
				} catch (ParseException | IOException e) {
					LOGGER.error("Exception in getResponseData");
				}
			}
			LOGGER.trace("getResponseData response data - {}",
					responseData);
		}
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResponseData() {

		return responseData;
	}

}
