package br.com.petfriends.pedido.infra.adapter.persistence.adapter;

import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.out.FindPedidoPort;
import br.com.petfriends.pedido.infra.adapter.persistence.mapper.PedidoInfraMapper;
import br.com.petfriends.pedido.infra.adapter.persistence.repository.PedidoMongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindPedidoAdapter implements FindPedidoPort {
    private final PedidoMongoRepository pedidoMongoRepository;
    private final PedidoInfraMapper pedidoInfraMapper;

    public FindPedidoAdapter(PedidoMongoRepository pedidoMongoRepository, PedidoInfraMapper pedidoInfraMapper) {
        this.pedidoMongoRepository = pedidoMongoRepository;
        this.pedidoInfraMapper = pedidoInfraMapper;
    }

    @Override
    public Optional<Pedido> findById(UUID id) {
        return pedidoMongoRepository.findById(id)
                .map(pedidoInfraMapper::toModel);
    }

    @Override
    public Optional<Pedido> findByEntregaId(String id) {
        return pedidoMongoRepository.findByEntregaId(id)
                .map(pedidoInfraMapper::toModel);
    }
}
