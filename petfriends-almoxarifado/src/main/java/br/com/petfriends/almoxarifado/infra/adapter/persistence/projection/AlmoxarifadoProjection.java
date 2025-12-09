package br.com.petfriends.almoxarifado.infra.adapter.persistence.projection;

import br.com.petfriends.almoxarifado.core.event.*;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper.AlmoxarifadoInfraMapper;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.repository.AlmoxarifadoMongoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AlmoxarifadoProjection {
    private final AlmoxarifadoMongoRepository almoxarifadoMongoRepository;
    private final AlmoxarifadoInfraMapper almoxarifadoInfraMapper;

    public AlmoxarifadoProjection(AlmoxarifadoMongoRepository almoxarifadoMongoRepository, AlmoxarifadoInfraMapper almoxarifadoInfraMapper) {
        this.almoxarifadoMongoRepository = almoxarifadoMongoRepository;
        this.almoxarifadoInfraMapper = almoxarifadoInfraMapper;
    }

    @EventHandler
    public void on(AlmoxarifadoCriadoEvent event) {
        Almoxarifado almoxarifado = new Almoxarifado();
        almoxarifado.on(event);

        AlmoxarifadoEntity entity = almoxarifadoInfraMapper.toEntity(almoxarifado);
        almoxarifadoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(AlmoxarifadoItemAdicionadoEvent event) {
        Almoxarifado almoxarifado = resgatarAlmoxarifadoSalvo(event.getId());
        almoxarifado.on(event);

        AlmoxarifadoEntity entity = almoxarifadoInfraMapper.toEntity(almoxarifado);
        almoxarifadoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(AlmoxarifadoItemReservadoEvent event) {
        Almoxarifado almoxarifado = resgatarAlmoxarifadoSalvo(event.getId());
        almoxarifado.on(event);

        AlmoxarifadoEntity entity = almoxarifadoInfraMapper.toEntity(almoxarifado);
        almoxarifadoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(AlmoxarifadoReservaLiberadaEvent event) {
        Almoxarifado almoxarifado = resgatarAlmoxarifadoSalvo(event.getId());
        almoxarifado.on(event);

        AlmoxarifadoEntity entity = almoxarifadoInfraMapper.toEntity(almoxarifado);
        almoxarifadoMongoRepository.save(entity);
    }

    @EventHandler
    public void on(AlmoxarifadoEstoqueConsumidoEvent event) {
        Almoxarifado almoxarifado = resgatarAlmoxarifadoSalvo(event.getId());
        almoxarifado.on(event);

        AlmoxarifadoEntity entity = almoxarifadoInfraMapper.toEntity(almoxarifado);
        almoxarifadoMongoRepository.save(entity);
    }

    private Almoxarifado resgatarAlmoxarifadoSalvo(UUID id) {
        Optional<Almoxarifado> almoxarifadoOptional = almoxarifadoMongoRepository.findById(id)
                .map(almoxarifadoInfraMapper::toModel);
        if (almoxarifadoOptional.isEmpty()) {
            throw new IllegalStateException("Não foi possível continuar com a projeção do agregado.");
        }
        return almoxarifadoOptional.get();
    }
}
