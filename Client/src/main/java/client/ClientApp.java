package client;

import client.Service.ServiceClientImpl;
import client.TCP.TCPClient;
import client.UI.ClientConsole;
import common.ServiceInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());
        TCPClient tcpClient = new TCPClient(ServiceInterface.SERVER_HOST,
                ServiceInterface.SERVER_PORT);
        ServiceInterface service =
                new ServiceClientImpl(executorService, tcpClient);
        ClientConsole clientConsole = new ClientConsole(service);

        clientConsole.runConsole();

        executorService.shutdownNow();

        System.out.println("client - bye");

    }
}
