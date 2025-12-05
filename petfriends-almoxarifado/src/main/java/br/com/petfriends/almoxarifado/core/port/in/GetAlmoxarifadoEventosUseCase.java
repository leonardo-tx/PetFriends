package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.query.BuscarAlmoxarifadoEventosPeloIdQuery;

import java.util.List;

public interface GetAlmoxarifadoEventosUseCase {
    List<Object> getEventos(BuscarAlmoxarifadoEventosPeloIdQuery query);
}
