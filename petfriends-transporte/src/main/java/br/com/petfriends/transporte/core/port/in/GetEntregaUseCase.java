package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloIdQuery;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloPedidoIdQuery;

public interface GetEntregaUseCase {
    Entrega getById(BuscarEntregaPeloIdQuery query);
    Entrega getByPedidoId(BuscarEntregaPeloPedidoIdQuery query);
}
