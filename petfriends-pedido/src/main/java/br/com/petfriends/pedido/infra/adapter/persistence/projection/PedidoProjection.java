package br.com.petfriends.pedido.infra.adapter.persistence.projection;

import br.com.petfriends.pedido.core.event.PedidoCanceladoEvent;
import br.com.petfriends.pedido.core.event.PedidoCriadoEvent;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.mapper.PedidoInfraMapper;
import br.com.petfriends.pedido.infra.adapter.persistence.repository.PedidoMongoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PedidoProjection {
    private final PedidoMongoRepository pedidoMongoRepository;
    private final PedidoInfraMapper pedidoInfraMapper;

    public PedidoProjection(PedidoMongoRepository pedidoMongoRepository, PedidoInfraMapper pedidoInfraMapper) {
        this.pedidoMongoRepository = pedidoMongoRepository;
        this.pedidoInfraMapper = pedidoInfraMapper;
    }

    @EventHandler
    public void on(PedidoCriadoEvent event) {
        Pedido pedido = new Pedido();
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(PedidoCanceladoEvent event) {
        Optional<Pedido> pedidoOptional = pedidoMongoRepository.findById(event.getId())
                .map(pedidoInfraMapper::toModel);
        if (pedidoOptional.isEmpty()) {
            throw new IllegalStateException("Não foi possível continuar com a projeção do agregado.");
        }
        Pedido pedido = pedidoOptional.get();
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }
}
