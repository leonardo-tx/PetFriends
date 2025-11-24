package br.com.petfriends.pedido.core.port.out;

import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;

import java.util.Optional;

public interface FindPedidoPort {
    Optional<Pedido> findById(BuscarPedidoPeloIdQuery query);
}
