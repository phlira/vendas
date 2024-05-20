package br.com.havila.service.impl;

import br.com.havila.domain.entity.Cliente;
import br.com.havila.domain.entity.ItemPedido;
import br.com.havila.domain.entity.Pedido;
import br.com.havila.domain.entity.Produto;
import br.com.havila.domain.enums.StatusPedido;
import br.com.havila.domain.repository.Clientes;
import br.com.havila.domain.repository.ItensPedido;
import br.com.havila.domain.repository.Pedidos;
import br.com.havila.domain.repository.Produtos;
import br.com.havila.exception.PedidoNaoEncontradoException;
import br.com.havila.exception.RegraNegocioException;
import br.com.havila.rest.dto.ItemPedidoDTO;
import br.com.havila.rest.dto.PedidoDTO;
import br.com.havila.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidos;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itemPedido = converterItens(pedido, dto.getItens());
        pedidos.save(pedido);
        itensPedidoRepository.saveAll(itemPedido);
        pedido.setItens(itemPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidos.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidos.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidos.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw  new RegraNegocioException("Nao é possível realizar um pedido sem itens.");
        }

        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository
                                .findById(idProduto)
                                .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
