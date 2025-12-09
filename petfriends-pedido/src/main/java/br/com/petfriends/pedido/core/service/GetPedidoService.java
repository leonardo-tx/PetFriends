package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.exception.PedidoNaoEncontradoException;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.in.GetPedidoUseCase;
import br.com.petfriends.pedido.core.port.out.FindPedidoPort;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloEntregaIdQuery;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;
import org.springframework.stereotype.Service;

@Service
public class GetPedidoService implements GetPedidoUseCase {
    private final FindPedidoPort findPedidoPort;

    public GetPedidoService(FindPedidoPort findPedidoPort) {
        this.findPedidoPort = findPedidoPort;
    }

    @Override
    public Pedido getById(BuscarPedidoPeloIdQuery query) {
        return findPedidoPort.findById(query.getId())
                .orElseThrow(PedidoNaoEncontradoException::new);
    }

    @Override
    public Pedido getByEntregaId(BuscarPedidoPeloEntregaIdQuery query) {
        return findPedidoPort.findByEntregaId(query.getId())
                .orElseThrow(PedidoNaoEncontradoException::new);
    }
}
