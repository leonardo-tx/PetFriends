package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;

public interface GetPedidoUseCase {
    Pedido getById(BuscarPedidoPeloIdQuery query);
}
