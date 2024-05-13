package br.com.havila.domain.repositorio;

import br.com.havila.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

//    private static String INSERT = "insert into cliente (nome) values (?) ";
//    private static String SELECT_ALL = "select * from cliente ";
//    private static String UPDATE = "update cliente set nome = ? where id = ?";
//    private static String DELETE = "delete from cliente where id = ?";



//    @Autowired
//    JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente) {
//        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        entityManager.persist(cliente);
        return cliente;

    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
//        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
//        jdbcTemplate.update(DELETE, new Object[]{cliente.getId()});
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "select c from Cliente c where c.nome like :nome ";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
//        return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"),
//                                new Object[]{"%"+nome+"%"},
//                                obterClienteMapper());
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos() {
        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
//        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

//    private static RowMapper<Cliente> obterClienteMapper() {
//        return new RowMapper<Cliente>() {
//            @Override
//            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
//                String nome = resultSet.getString("nome");
//                Integer id = resultSet.getInt("id");
//                return new Cliente(id, nome);
//            }
//        };
//    }
}
