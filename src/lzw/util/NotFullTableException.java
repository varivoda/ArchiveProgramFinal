package lzw.util;

public class NotFullTableException extends Exception {

    public NotFullTableException(final String message) {
        super(message);
    }

    public NotFullTableException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

