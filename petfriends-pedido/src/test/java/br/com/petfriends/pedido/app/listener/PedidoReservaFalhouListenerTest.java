package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.PedidoReservaFalhouEventDTO;
import br.com.petfriends.pedido.core.command.CancelarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.CancelarPedidoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoReservaFalhouListenerTest {
    @Mock
    private CancelarPedidoUseCase cancelarPedidoUseCase;

    @InjectMocks
    private PedidoReservaFalhouListener listener;

    @Captor
    private ArgumentCaptor<CancelarPedidoCommand> commandCaptor;

    @Test
    void deveChamarUseCaseQuandoReceberEvento() {
        UUID pedidoId = UUID.randomUUID();
        PedidoReservaFalhouEventDTO eventDTO = new PedidoReservaFalhouEventDTO(pedidoId, Instant.now());

        Consumer<PedidoReservaFalhouEventDTO> consumer = listener.pedidoReservaFalhou();
        consumer.accept(eventDTO);

        verify(cancelarPedidoUseCase, times(1)).cancelar(commandCaptor.capture());

        CancelarPedidoCommand command = commandCaptor.getValue();
        assertEquals(pedidoId, command.getId());
    }
}
