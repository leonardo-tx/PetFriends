package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNaoEncontradoException;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.in.GetAlmoxarifadoUseCase;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import br.com.petfriends.almoxarifado.core.query.BuscarAlmoxarifadoPeloIdQuery;
import org.springframework.stereotype.Service;

@Service
public class GetAlmoxarifadoService implements GetAlmoxarifadoUseCase {
    private final FindAlmoxarifadoPort findAlmoxarifadoPort;

    public GetAlmoxarifadoService(FindAlmoxarifadoPort findAlmoxarifadoPort) {
        this.findAlmoxarifadoPort = findAlmoxarifadoPort;
    }

    @Override
    public Almoxarifado getById(BuscarAlmoxarifadoPeloIdQuery query) {
        return findAlmoxarifadoPort.findById(query.getId())
                .orElseThrow(AlmoxarifadoNaoEncontradoException::new);
    }
}
