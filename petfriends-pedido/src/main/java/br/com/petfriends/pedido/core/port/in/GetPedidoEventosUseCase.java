package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.query.BuscarPedidoEventosPeloIdQuery;

import java.util.List;

public interface GetPedidoEventosUseCase {
    List<Object> getEventos(BuscarPedidoEventosPeloIdQuery query);
}
