package br.com.petfriends.transporte.infra.adapter.persistence.projection;

import br.com.petfriends.transporte.core.event.*;
import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.EntregaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.mapper.EntregaInfraMapper;
import br.com.petfriends.transporte.infra.adapter.persistence.repository.EntregaMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntregaProjectionTest {

    @Mock
    private EntregaMongoRepository entregaMongoRepository;

    @Mock
    private EntregaInfraMapper entregaInfraMapper;

    @InjectMocks
    private EntregaProjection entregaProjection;

    @Mock
    private Entrega entrega;

    @Mock
    private EntregaEntity entregaEntity;

    @Test
    void deveSalvarEntregaAoReceberEntregaCriadaEvent() {
        EntregaCriadaEvent event = new EntregaCriadaEvent(UUID.randomUUID(), Instant.now(), "pedido-1", List.of());

        when(entregaInfraMapper.toEntity(any(Entrega.class))).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberEntregaCanceladaEvent() {
        UUID id = UUID.randomUUID();
        EntregaCanceladaEvent event = new EntregaCanceladaEvent(id, Instant.now());

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberEntregaIniciadaEvent() {
        UUID id = UUID.randomUUID();
        EntregaIniciadaEvent event = new EntregaIniciadaEvent(id, Instant.now());

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberEntregaEntregueEvent() {
        UUID id = UUID.randomUUID();
        EntregaEntregueEvent event = new EntregaEntregueEvent(id, Instant.now());

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberEntregaSeparadaEvent() {
        UUID id = UUID.randomUUID();
        EntregaSeparadaEvent event = new EntregaSeparadaEvent(id, Instant.now());

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberEntregaTransportadaEvent() {
        UUID id = UUID.randomUUID();
        EntregaTransportadaEvent event = new EntregaTransportadaEvent(id, Instant.now());

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberRemessaEntregueEvent() {
        UUID id = UUID.randomUUID();
        String almoxarifadoId = UUID.randomUUID().toString();
        RemessaEntregueEvent event = new RemessaEntregueEvent(id, Instant.now(), almoxarifadoId);

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberRemessaOcorrenciaCriadaEvent() {
        UUID id = UUID.randomUUID();
        String almoxarifadoId = UUID.randomUUID().toString();
        RemessaOcorrenciaCriadaEvent event = new RemessaOcorrenciaCriadaEvent(id, Instant.now(), almoxarifadoId);

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberRemessaSeparadaEvent() {
        UUID id = UUID.randomUUID();
        String almoxarifadoId = UUID.randomUUID().toString();
        RemessaSeparadaEvent event = new RemessaSeparadaEvent(id, Instant.now(), almoxarifadoId);

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveSalvarEntregaAoReceberRemessaTransportadaEvent() {
        UUID id = UUID.randomUUID();
        String almoxarifadoId = UUID.randomUUID().toString();
        RemessaTransportadaEvent event = new RemessaTransportadaEvent(id, Instant.now(), almoxarifadoId);

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.of(entregaEntity));
        when(entregaInfraMapper.toModel(entregaEntity)).thenReturn(entrega);
        when(entregaInfraMapper.toEntity(entrega)).thenReturn(entregaEntity);

        entregaProjection.on(event);

        verify(entregaMongoRepository).save(entregaEntity);
    }

    @Test
    void deveLancarExcecaoSeEntregaNaoExistir() {
        UUID id = UUID.randomUUID();
        EntregaCanceladaEvent event = new EntregaCanceladaEvent(id, Instant.now());

        when(entregaMongoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> entregaProjection.on(event));
    }
}
