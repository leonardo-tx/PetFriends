package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.query.BuscarAlmoxarifadoPeloIdQuery;

public interface GetAlmoxarifadoUseCase {
    Almoxarifado getById(BuscarAlmoxarifadoPeloIdQuery query);
}
