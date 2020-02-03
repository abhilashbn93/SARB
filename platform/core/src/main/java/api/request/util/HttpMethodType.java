package sarb.api.request.util;

public enum HttpMethodType {

    /**
     * select
     */
    GET,

    /**
     * edit
     */
    POST,

    /**
     * add
     */
    PUT,
    /**
     * DELETE
     */
    DELETE;

	@Override
    public String toString() {
        return name();
    }
}
