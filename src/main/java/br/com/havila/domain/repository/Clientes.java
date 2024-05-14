package br.com.havila.domain.repository;

import br.com.havila.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    //@Query(value = "select * from cliente c where c.nome like %:nome% ", nativeQuery = true)
    @Query(value = " select c from Cliente c where c.nome like %:nome% ")
    List<Cliente> findByNomeLike(@Param("nome") String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
