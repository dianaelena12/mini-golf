package server.Config;

import common.ServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import server.Service.StudentServiceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ServerAppConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("ServiceInterface");
        exporter.setServiceInterface(ServiceInterface.class);
        exporter.setService(studentService());
//        exporter.setRegistryHost("192.168.0.199");
//        exporter.setRegistryPort(1098);
        System.out.println(exporter);
        return exporter;
    }

    @Bean
    ServiceInterface studentService() {
        ServiceInterface service = new StudentServiceImpl();
        return service;
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }
}
