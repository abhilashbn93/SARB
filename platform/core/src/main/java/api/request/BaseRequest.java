package sarb.api.request;

import sarb.api.request.util.HttpMethodType;


public interface BaseRequest {

    String getUrl();

    HttpMethodType gethttpMethodType();

    String getData();

}
