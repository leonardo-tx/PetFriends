package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.query.BuscarEntregaEventosPeloIdQuery;

import java.util.List;

public interface GetEntregaEventosUseCase {
    List<Object> getEventos(BuscarEntregaEventosPeloIdQuery query);
}
