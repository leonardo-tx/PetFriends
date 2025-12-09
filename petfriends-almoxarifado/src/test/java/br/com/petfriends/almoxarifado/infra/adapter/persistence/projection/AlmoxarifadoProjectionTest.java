package br.com.petfriends.almoxarifado.infra.adapter.persistence.projection;

import br.com.petfriends.almoxarifado.core.event.*;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper.AlmoxarifadoInfraMapper;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.repository.AlmoxarifadoMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlmoxarifadoProjectionTest {
    @Mock
    private AlmoxarifadoMongoRepository almoxarifadoMongoRepository;

    @Mock
    private AlmoxarifadoInfraMapper almoxarifadoInfraMapper;

    @InjectMocks
    private AlmoxarifadoProjection projection;

    @Test
    void deveSalvarAlmoxarifadoQuandoEventoCriado() {
        AlmoxarifadoCriadoEvent event = mock(AlmoxarifadoCriadoEvent.class);
        Almoxarifado almox = mock(Almoxarifado.class);
        AlmoxarifadoEntity entity = mock(AlmoxarifadoEntity.class);

        when(almoxarifadoInfraMapper.toEntity(any())).thenReturn(entity);

        projection.on(event);

        verify(almoxarifadoMongoRepository, times(1)).save(entity);
    }

    @Test
    void deveSalvarAlmoxarifadoQuandoEventoItemAdicionado() {
        UUID id = UUID.randomUUID();
        AlmoxarifadoItemAdicionadoEvent event = mock(AlmoxarifadoItemAdicionadoEvent.class);
        when(event.getId()).thenReturn(id);

        Almoxarifado almox = mock(Almoxarifado.class);
        AlmoxarifadoEntity entity = mock(AlmoxarifadoEntity.class);

        when(almoxarifadoMongoRepository.findById(id)).thenReturn(Optional.of(mock(AlmoxarifadoEntity.class)));
        when(almoxarifadoInfraMapper.toModel(any())).thenReturn(almox);
        when(almoxarifadoInfraMapper.toEntity(almox)).thenReturn(entity);

        projection.on(event);

        verify(almoxarifadoMongoRepository, times(1)).save(entity);
    }

    @Test
    void deveLancarExcecaoQuandoAlmoxarifadoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        AlmoxarifadoItemReservadoEvent event = mock(AlmoxarifadoItemReservadoEvent.class);
        when(event.getId()).thenReturn(id);

        when(almoxarifadoMongoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> projection.on(event));
    }

    @Test
    void deveSalvarAlmoxarifadoParaTodosOsEventosRestantes() {
        UUID id = UUID.randomUUID();
        Almoxarifado almox = mock(Almoxarifado.class);
        AlmoxarifadoEntity entity = mock(AlmoxarifadoEntity.class);

        when(almoxarifadoMongoRepository.findById(id)).thenReturn(Optional.of(mock(AlmoxarifadoEntity.class)));
        when(almoxarifadoInfraMapper.toModel(any())).thenReturn(almox);
        when(almoxarifadoInfraMapper.toEntity(almox)).thenReturn(entity);

        AlmoxarifadoReservaLiberadaEvent reservaLiberadaEvent = mock(AlmoxarifadoReservaLiberadaEvent.class);
        when(reservaLiberadaEvent.getId()).thenReturn(id);
        projection.on(reservaLiberadaEvent);
        verify(almoxarifadoMongoRepository, times(1)).save(entity);

        AlmoxarifadoEstoqueConsumidoEvent estoqueConsumidoEvent = mock(AlmoxarifadoEstoqueConsumidoEvent.class);
        when(estoqueConsumidoEvent.getId()).thenReturn(id);
        projection.on(estoqueConsumidoEvent);
        verify(almoxarifadoMongoRepository, times(2)).save(entity);
    }
}
