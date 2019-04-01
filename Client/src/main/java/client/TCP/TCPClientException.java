package client.TCP;

public class TCPClientException extends RuntimeException{
    public TCPClientException(String message) {
        super(message);
    }

    public TCPClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public TCPClientException(Throwable cause) {
        super(cause);
    }
}
