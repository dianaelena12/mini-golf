package client;

import client.UI.ClientConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;

public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("client/Config");
        ClientConsole clientConsole = context.getBean(ClientConsole.class);
        clientConsole.runConsole();
        System.out.println("client - bye");

//        ExecutorService executorService =
//                Executors.newFixedThreadPool(
//                        Runtime.getRuntime().availableProcessors());
//        TCPClient tcpClient = new TCPClient(ServiceInterface.SERVER_HOST,
//                ServiceInterface.SERVER_PORT);
//        ServiceInterface service =
//                new ServiceClientImpl(executorService, tcpClient);
//        ClientConsole clientConsole = new ClientConsole(service);
//
//        clientConsole.runConsole();
//
//        executorService.shutdownNow();

    }
}
