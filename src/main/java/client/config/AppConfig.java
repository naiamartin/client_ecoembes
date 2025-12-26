package client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import client.proxies.IEcoembesServiceProxy;
import client.proxies.RestTemplateServiceProxy;

@Configuration
public class AppConfig {
    @Bean
    public IEcoembesServiceProxy ecoembesServiceProxy() {
        return new RestTemplateServiceProxy();
    }
}
