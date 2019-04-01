package client.TCP;

import common.Message;
import common.ServerException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    private String host;
    private int port;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Message sendAndReceive(Message request) {
        try (Socket socket = new Socket(host, port);
             InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream()) {

            request.writeTo(os);
            System.out.println("client - sent request: " + request);

            Message response = Message.builder().build();
            response.readFrom(is);
            System.out.println("client - received response: " + response);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException("client - exception connecting to" +
                    " server", e);
        }
    }
}
