package moe.vergo.seasonalseiyuuapi.application.exception;

public class GetSeiyuuDetailsException extends RuntimeException {
    public GetSeiyuuDetailsException() {
        super();
    }

    public GetSeiyuuDetailsException(String message) {
        super(message);
    }

    public GetSeiyuuDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetSeiyuuDetailsException(Throwable cause) {
        super(cause);
    }

    public GetSeiyuuDetailsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
