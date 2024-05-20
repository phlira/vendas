package br.com.havila.service;

import br.com.havila.domain.entity.Pedido;
import br.com.havila.domain.enums.StatusPedido;
import br.com.havila.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
