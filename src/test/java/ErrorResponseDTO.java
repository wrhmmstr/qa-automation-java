public class ErrorResponseDTO {
    private String timestamp;
    private String error;
    private String type;
    private String title;
    private String status;
    private String detail;
    private String path;
    private String message;
    private String entityName;
    private String errorKey;
    private String params;
    private Object fieldErrors;


    public String getEntityName() {
        return entityName;
    }

    public ErrorResponseDTO setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public ErrorResponseDTO setErrorKey(String errorKey) {
        this.errorKey = errorKey;
        return this;
    }

    public String getParams() {
        return params;
    }

    public ErrorResponseDTO setParams(String params) {
        this.params = params;
        return this;
    }

    public Object getFieldErrors() {
        return fieldErrors;
    }

    public ErrorResponseDTO setFieldErrors(Object fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }

    public String getError() {
        return error;
    }

    public ErrorResponseDTO setError(String error) {
        this.error = error;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ErrorResponseDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ErrorResponseDTO setType(String type) {
        this.type = type;
        return this;
    }

    public ErrorResponseDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public ErrorResponseDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public ErrorResponseDTO setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public ErrorResponseDTO setPath(String path) {
        this.path = path;
        return this;
    }

    public ErrorResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
