package br.com.petfriends.pedido.infra.adapter.persistence.projection;

import br.com.petfriends.pedido.core.event.*;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.mapper.PedidoInfraMapper;
import br.com.petfriends.pedido.infra.adapter.persistence.repository.PedidoMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoProjectionTest {

    @Mock
    private PedidoMongoRepository pedidoMongoRepository;

    @Mock
    private PedidoInfraMapper pedidoInfraMapper;

    @InjectMocks
    private PedidoProjection pedidoProjection;

    @Captor
    private ArgumentCaptor<PedidoEntity> pedidoEntityCaptor;

    private UUID pedidoId;

    @BeforeEach
    void setupBeforeEach() {
        pedidoId = UUID.randomUUID();
    }

    @Test
    void deveSalvarPedidoCriadoEvent() {
        PedidoCriadoEvent event = new PedidoCriadoEvent(
                pedidoId,
                java.time.Instant.now(),
                "cliente-1",
                null,
                List.of()
        );

        Pedido pedido = new Pedido();
        PedidoEntity entity = mock(PedidoEntity.class);

        when(pedidoInfraMapper.toEntity(any(Pedido.class))).thenReturn(entity);

        pedidoProjection.on(event);

        verify(pedidoMongoRepository, times(1)).save(entity);
    }

    @Test
    void deveSalvarPedidoIniciadoEvent() {
        PedidoIniciadoEvent event = new PedidoIniciadoEvent(pedidoId, java.time.Instant.now());
        Pedido pedido = mock(Pedido.class);
        PedidoEntity entity = mock(PedidoEntity.class);

        when(pedidoMongoRepository.findById(pedidoId))
                .thenReturn(Optional.of(entity));
        when(pedidoInfraMapper.toModel(entity)).thenReturn(pedido);
        when(pedidoInfraMapper.toEntity(pedido)).thenReturn(entity);

        pedidoProjection.on(event);

        verify(pedidoMongoRepository, times(1)).save(entity);
    }

    @Test
    void deveLancarExcecaoSePedidoNaoExiste() {
        PedidoIniciadoEvent event = new PedidoIniciadoEvent(pedidoId, java.time.Instant.now());

        when(pedidoMongoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        try {
            pedidoProjection.on(event);
        } catch (IllegalStateException e) {
            assertEquals("Não foi possível continuar com a projeção do agregado.", e.getMessage());
        }

        verify(pedidoMongoRepository, never()).save(any());
    }

    @Test
    void deveSalvarTodosOsEventosRestantes() {
        List<Object> eventos = List.of(
                new PedidoCanceladoEvent(pedidoId, java.time.Instant.now()),
                new PedidoPagoEvent(pedidoId, java.time.Instant.now()),
                new PedidoSeparadoEvent(pedidoId, java.time.Instant.now()),
                new PedidoEnviadoEvent(pedidoId, java.time.Instant.now()),
                new PedidoEntregueEvent(pedidoId, java.time.Instant.now()),
                new EntregaAdicionadaAoPedidoEvent(pedidoId, java.time.Instant.now(), "entrega-123")
        );

        for (Object event : eventos) {
            Pedido pedido = mock(Pedido.class);
            PedidoEntity entity = mock(PedidoEntity.class);

            when(pedidoMongoRepository.findById(pedidoId)).thenReturn(Optional.of(entity));
            when(pedidoInfraMapper.toModel(entity)).thenReturn(pedido);
            when(pedidoInfraMapper.toEntity(pedido)).thenReturn(entity);

            if (event instanceof PedidoCanceladoEvent e) pedidoProjection.on(e);
            if (event instanceof PedidoPagoEvent e) pedidoProjection.on(e);
            if (event instanceof PedidoSeparadoEvent e) pedidoProjection.on(e);
            if (event instanceof PedidoEnviadoEvent e) pedidoProjection.on(e);
            if (event instanceof PedidoEntregueEvent e) pedidoProjection.on(e);
            if (event instanceof EntregaAdicionadaAoPedidoEvent e) pedidoProjection.on(e);

            verify(pedidoMongoRepository, times(1)).save(entity);
            reset(pedidoMongoRepository);
        }
    }
}
