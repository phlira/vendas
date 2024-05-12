package br.com.havila;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
public class ConfiguacoesProjeto {

    @Bean
    public CommandLineRunner executar() {
        return args -> {
            System.out.println("RODANDO EM AMBIENTE DE DESENVOLVIMENTO");
        };
    }
}
