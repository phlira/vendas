package br.com.havila;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguacoesProjeto {

    @Bean(name = "applicationName")
    public String applicationName() {
        return "Sistema de vendas Havilá Consultoria";
    }
}
