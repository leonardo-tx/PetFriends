package br.com.petfriends.almoxarifado.infra.adapter.messenger.publisher;

import br.com.petfriends.almoxarifado.core.event.PedidoReservaConcluidoEvent;
import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoReserva;
import br.com.petfriends.almoxarifado.infra.adapter.messenger.dto.PedidoReservaConcluidoEventDTO;
import br.com.petfriends.almoxarifado.infra.adapter.messenger.dto.ReservaEventDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoReservaConcluidoPublisherTest {
    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PedidoReservaConcluidoPublisher publisher;

    @Test
    void devePublicarEventoNoStreamBridge() {
        String pedidoId = UUID.randomUUID().toString();
        List<AlmoxarifadoReserva> reservas = List.of(
                new AlmoxarifadoReserva(UUID.randomUUID(), "item-1", 5),
                new AlmoxarifadoReserva(UUID.randomUUID(), "item-2", 3)
        );
        PedidoReservaConcluidoEvent event = new PedidoReservaConcluidoEvent(
                pedidoId,
                Instant.now(),
                reservas
        );

        publisher.on(event);

        ArgumentCaptor<PedidoReservaConcluidoEventDTO> captor = ArgumentCaptor.forClass(PedidoReservaConcluidoEventDTO.class);
        verify(streamBridge, times(1)).send(eq("pedido-reserva-concluido-out-0"), captor.capture());

        PedidoReservaConcluidoEventDTO dto = captor.getValue();
        assertEquals(pedidoId, dto.id());
        assertEquals(reservas.size(), dto.reservas().size());

        for (int i = 0; i < reservas.size(); i++) {
            AlmoxarifadoReserva r = reservas.get(i);
            ReservaEventDTO dtoReserva = dto.reservas().get(i);
            assertEquals(r.almoxarifadoId().toString(), dtoReserva.almoxarifadoId());
            assertEquals(r.itemId(), dtoReserva.itemId());
            assertEquals(r.quantidade(), dtoReserva.quantidade());
        }
    }
}
