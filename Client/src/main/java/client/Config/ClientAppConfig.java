package client.Config;

import client.UI.ClientConsole;
import common.ServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientAppConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(ServiceInterface.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ServiceInterface");
        System.out.println(rmiProxyFactoryBean);
        return rmiProxyFactoryBean;
    }


    @Bean
    ClientConsole clientConsole() {
        return new ClientConsole();
    }
}
