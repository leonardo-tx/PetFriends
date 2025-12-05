package br.com.petfriends.transporte.infra.adapter.persistence.projection;

import br.com.petfriends.transporte.core.event.*;
import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.EntregaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.mapper.EntregaInfraMapper;
import br.com.petfriends.transporte.infra.adapter.persistence.repository.EntregaMongoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EntregaProjection {
    private final EntregaMongoRepository entregaMongoRepository;
    private final EntregaInfraMapper entregaInfraMapper;

    public EntregaProjection(EntregaMongoRepository entregaMongoRepository, EntregaInfraMapper entregaInfraMapper) {
        this.entregaMongoRepository = entregaMongoRepository;
        this.entregaInfraMapper = entregaInfraMapper;
    }

    @EventHandler
    public void on(EntregaCriadaEvent event) {
        Entrega entrega = new Entrega();
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(EntregaCanceladaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(EntregaIniciadaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(EntregaEntregueEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(EntregaSeparadaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(EntregaTransportadaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(RemessaEntregueEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(RemessaOcorrenciaCriadaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(RemessaSeparadaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    @EventHandler
    public void on(RemessaTransportadaEvent event) {
        Entrega entrega = resgatarEntregaSalvo(event.getId());
        entrega.on(event);

        EntregaEntity entity = entregaInfraMapper.toEntity(entrega);
        entregaMongoRepository.save(entity);
    }

    private Entrega resgatarEntregaSalvo(UUID id) {
        Optional<Entrega> entregaOptional = entregaMongoRepository.findById(id)
                .map(entregaInfraMapper::toModel);
        if (entregaOptional.isEmpty()) {
            throw new IllegalStateException("Não foi possível continuar com a projeção do agregado.");
        }
        return entregaOptional.get();
    }
}
