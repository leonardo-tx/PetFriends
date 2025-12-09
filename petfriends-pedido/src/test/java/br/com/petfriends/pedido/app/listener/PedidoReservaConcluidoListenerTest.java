package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.PedidoReservaConcluidoEventDTO;
import br.com.petfriends.pedido.core.command.IniciarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.IniciarPedidoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoReservaConcluidoListenerTest {
    @Mock
    private IniciarPedidoUseCase iniciarPedidoUseCase;

    @InjectMocks
    private PedidoReservaConcluidoListener listener;

    @Captor
    private ArgumentCaptor<IniciarPedidoCommand> commandCaptor;

    @Test
    void deveChamarUseCaseQuandoReceberEvento() {
        UUID pedidoId = UUID.randomUUID();
        PedidoReservaConcluidoEventDTO eventDTO = new PedidoReservaConcluidoEventDTO(pedidoId, Instant.now(), List.of());

        Consumer<PedidoReservaConcluidoEventDTO> consumer = listener.pedidoReservaConcluido();
        consumer.accept(eventDTO);

        verify(iniciarPedidoUseCase, times(1)).iniciar(commandCaptor.capture());

        IniciarPedidoCommand command = commandCaptor.getValue();
        assertEquals(pedidoId, command.getId());
    }
}
