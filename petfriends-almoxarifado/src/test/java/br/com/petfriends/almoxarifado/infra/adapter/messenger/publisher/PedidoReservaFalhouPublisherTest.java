package br.com.petfriends.almoxarifado.infra.adapter.messenger.publisher;

import br.com.petfriends.almoxarifado.core.event.PedidoReservaFalhouEvent;
import br.com.petfriends.almoxarifado.infra.adapter.messenger.dto.PedidoReservaFalhouEventDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoReservaFalhouPublisherTest {

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PedidoReservaFalhouPublisher publisher;

    @Test
    void devePublicarEventoNoStreamBridge() {
        String pedidoId = UUID.randomUUID().toString();
        PedidoReservaFalhouEvent event = new PedidoReservaFalhouEvent(
                pedidoId,
                Instant.now()
        );

        publisher.on(event);

        ArgumentCaptor<PedidoReservaFalhouEventDTO> captor = ArgumentCaptor.forClass(PedidoReservaFalhouEventDTO.class);
        verify(streamBridge, times(1)).send(eq("pedido-reserva-falhou-out-0"), captor.capture());

        PedidoReservaFalhouEventDTO dto = captor.getValue();
        assertEquals(pedidoId, dto.id());
        assertEquals(event.getTimestamp(), dto.timestamp());
    }
}
