package br.com.petfriends.pedido.infra.adapter.messenger.adapter;

import br.com.petfriends.pedido.core.event.PedidoPagoEvent;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoPagoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.publisher.PedidoPagoPublisher;
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
class PedidoPagoPublisherTest {
    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PedidoPagoPublisher publisher;

    @Captor
    private ArgumentCaptor<PedidoPagoEventDTO> captor;

    private UUID pedidoId;
    private Instant timestamp;

    @BeforeEach
    void setupBeforeEach() {
        pedidoId = UUID.randomUUID();
        timestamp = Instant.now();
    }

    @Test
    void devePublicarEventoPagoNoStreamBridge() {
        PedidoPagoEvent event = new PedidoPagoEvent(pedidoId, timestamp);
        publisher.on(event);

        verify(streamBridge).send(eq("pedido-pago-out-0"), captor.capture());

        PedidoPagoEventDTO dto = captor.getValue();
        assertEquals(pedidoId, dto.id());
        assertEquals(timestamp, dto.timestamp());
    }
}
