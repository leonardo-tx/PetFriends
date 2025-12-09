package br.com.petfriends.pedido.infra.adapter.messenger.adapter;

import br.com.petfriends.pedido.core.event.PedidoSeparadoEvent;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoSeparadoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.publisher.PedidoSeparadoPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoSeparadoPublisherTest {
    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PedidoSeparadoPublisher publisher;

    @Captor
    private ArgumentCaptor<PedidoSeparadoEventDTO> captor;

    private UUID pedidoId;
    private Instant timestamp;

    @BeforeEach
    void setupBeforeEach() {
        pedidoId = UUID.randomUUID();
        timestamp = Instant.now();
    }

    @Test
    void devePublicarEventoSeparadoNoStreamBridge() {
        PedidoSeparadoEvent event = new PedidoSeparadoEvent(pedidoId, timestamp);
        publisher.on(event);

        verify(streamBridge).send(eq("pedido-separado-out-0"), captor.capture());

        PedidoSeparadoEventDTO dto = captor.getValue();
        assertEquals(pedidoId, dto.id());
        assertEquals(timestamp, dto.timestamp());
    }
}
