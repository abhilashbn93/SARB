package sarb.api.request.impl;

import sarb.api.request.BaseRequest;
import sarb.api.request.util.HttpMethodType;


public class BaseRequestImpl implements BaseRequest {
	

    private String url;
    private HttpMethodType methodtype;
    private String data;

    public BaseRequestImpl(final String url, final HttpMethodType method, final String data) {
        this.url = url;
        this.methodtype = method;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public String getData() {
        return data;
    }

    public HttpMethodType gethttpMethodType() {
        return methodtype;
    }

}
