package server.TCP;

public class TCPServerException extends RuntimeException {
    public TCPServerException(String message) {
        super(message);
    }

    public TCPServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TCPServerException(Throwable cause) {
        super(cause);
    }
}