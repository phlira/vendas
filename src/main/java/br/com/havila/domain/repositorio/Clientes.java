package br.com.havila.domain.repositorio;

import br.com.havila.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    //@Query(value = "select * from cliente c where c.nome like %:nome% ", nativeQuery = true)
    @Query(value = " select c from Cliente c where c.nome like %:nome% ")
    List<Cliente> findByNomeLike(@Param("nome") String nome);
}
