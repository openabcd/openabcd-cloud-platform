package io.github.openabcd.cloud.common.exception;

public class JsonConvertException extends RuntimeException {

    public JsonConvertException(String message) {
        super(message);
    }

    public JsonConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonConvertException(Throwable cause) {
        super(cause);
    }
}
