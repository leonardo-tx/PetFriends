package br.com.petfriends.pedido.infra.adapter.persistence.adapter;

import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.out.FindPedidoPort;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;
import br.com.petfriends.pedido.infra.adapter.persistence.mapper.PedidoInfraMapper;
import br.com.petfriends.pedido.infra.adapter.persistence.repository.PedidoMongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindPedidoAdapter implements FindPedidoPort {
    private final PedidoMongoRepository pedidoMongoRepository;
    private final PedidoInfraMapper pedidoInfraMapper;

    public FindPedidoAdapter(PedidoMongoRepository pedidoMongoRepository, PedidoInfraMapper pedidoInfraMapper) {
        this.pedidoMongoRepository = pedidoMongoRepository;
        this.pedidoInfraMapper = pedidoInfraMapper;
    }

    @Override
    public Optional<Pedido> findById(BuscarPedidoPeloIdQuery query) {
        return pedidoMongoRepository.findById(query.getId())
                .map(pedidoInfraMapper::toModel);
    }
}
