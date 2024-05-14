package br.com.havila;

import br.com.havila.domain.entity.Cliente;
import br.com.havila.domain.entity.Pedido;
import br.com.havila.domain.repository.Clientes;
import br.com.havila.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired Clientes clientes,
            @Autowired Pedidos pedidos) {
        return args -> {
            Cliente pedro = new Cliente("Pedro Lira");
            clientes.save(pedro);
            clientes.save(new Cliente("Julia Lira"));

            Pedido pedido = new Pedido();
            pedido.setCliente(pedro);
            pedido.setDataPedido(LocalDate.now());
            pedido.setTotal(BigDecimal.valueOf(100));
            pedidos.save(pedido);

            Cliente cli = clientes.findClienteFetchPedidos(pedro.getId());
            System.out.println(cli);
            System.out.println(cli.getPedidos());

//            List<Cliente> todosClientes = clientes.findAll();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("Atualizando Clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " Atualizado");
//                clientes.save(c);
//            });
//
//            todosClientes = clientes.findAll();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("Buscando Clientes");
//            List<Cliente> clientesBusca = clientes.findByNomeLike("Lira");
//            System.out.println("Está vazia a lista? " + clientesBusca.isEmpty());
//            clientesBusca.forEach(System.out::println);
//
//            System.out.println("Deletando Clientes");
//            clientes.findAll().forEach(clientes::delete);
//
//            todosClientes = clientes.findAll();
//            if (todosClientes.isEmpty()) {
//                System.out.println("Nenhum cliente encontrado");
//            } else {
//                todosClientes.forEach(System.out::println);
//            }

        };
    }
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
