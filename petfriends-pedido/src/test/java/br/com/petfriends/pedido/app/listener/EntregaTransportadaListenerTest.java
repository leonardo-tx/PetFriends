package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaTransportadaEventDTO;
import br.com.petfriends.pedido.core.command.EnviarPedidoCommand;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.in.EnviarPedidoUseCase;
import br.com.petfriends.pedido.core.port.in.GetPedidoUseCase;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloEntregaIdQuery;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntregaTransportadaListenerTest {
    @Mock
    private EnviarPedidoUseCase enviarPedidoUseCase;

    @Mock
    private GetPedidoUseCase getPedidoUseCase;

    @InjectMocks
    private EntregaTransportadaListener listener;

    @Captor
    private ArgumentCaptor<EnviarPedidoCommand> commandCaptor;

    @Test
    void deveChamarUseCaseQuandoReceberEvento() {
        UUID pedidoId = UUID.randomUUID();
        String entregaId = UUID.randomUUID().toString();

        EntregaTransportadaEventDTO eventDTO = new EntregaTransportadaEventDTO(entregaId, Instant.now());

        Pedido pedido = mock(Pedido.class);
        when(getPedidoUseCase.getByEntregaId(any(BuscarPedidoPeloEntregaIdQuery.class))).thenReturn(pedido);
        when(pedido.getId()).thenReturn(pedidoId);

        Consumer<EntregaTransportadaEventDTO> consumer = listener.entregaTransportada();
        consumer.accept(eventDTO);

        verify(enviarPedidoUseCase, times(1)).enviar(commandCaptor.capture());

        EnviarPedidoCommand command = commandCaptor.getValue();
        assertEquals(pedidoId, command.getId());

        ArgumentCaptor<BuscarPedidoPeloEntregaIdQuery> queryCaptor = ArgumentCaptor.forClass(BuscarPedidoPeloEntregaIdQuery.class);
        verify(getPedidoUseCase, times(1)).getByEntregaId(queryCaptor.capture());
        assertEquals(entregaId, queryCaptor.getValue().getId());
    }
}
