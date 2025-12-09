package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.exception.EntregaNaoEncontradaException;
import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.port.in.GetEntregaUseCase;
import br.com.petfriends.transporte.core.port.out.FindEntregaPort;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloIdQuery;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloPedidoIdQuery;
import org.springframework.stereotype.Service;

@Service
public class GetEntregaService implements GetEntregaUseCase {
    private final FindEntregaPort findEntregaPort;

    public GetEntregaService(FindEntregaPort findPedidoPort) {
        this.findEntregaPort = findPedidoPort;
    }

    @Override
    public Entrega getById(BuscarEntregaPeloIdQuery query) {
        return findEntregaPort.findById(query.getId())
                .orElseThrow(EntregaNaoEncontradaException::new);
    }

    @Override
    public Entrega getByPedidoId(BuscarEntregaPeloPedidoIdQuery query) {
        return findEntregaPort.findByPedidoId(query.getId())
                .orElseThrow(EntregaNaoEncontradaException::new);
    }
}
