package br.com.petfriends.pedido.infra.adapter.persistence.projection;

import br.com.petfriends.pedido.core.event.*;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.mapper.PedidoInfraMapper;
import br.com.petfriends.pedido.infra.adapter.persistence.repository.PedidoMongoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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
    public void on(PedidoIniciadoEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(PedidoCanceladoEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(PedidoPagoEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(PedidoSeparadoEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(PedidoEnviadoEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(PedidoEntregueEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(EntregaAdicionadaAoPedidoEvent event) {
        Pedido pedido = resgatarPedidoSalvo(event.getId());
        pedido.on(event);

        PedidoEntity entity = pedidoInfraMapper.toEntity(pedido);
        pedidoMongoRepository.save(entity);
    }

    private Pedido resgatarPedidoSalvo(UUID id) {
        Optional<Pedido> pedidoOptional = pedidoMongoRepository.findById(id)
                .map(pedidoInfraMapper::toModel);
        if (pedidoOptional.isEmpty()) {
            throw new IllegalStateException("Não foi possível continuar com a projeção do agregado.");
        }
        return pedidoOptional.get();
    }
}
