package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaEntregueEventDTO;
import br.com.petfriends.pedido.core.command.EntregarPedidoCommand;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.in.EntregarPedidoUseCase;
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
class EntregaEntregueListenerTest {
    @Mock
    private EntregarPedidoUseCase entregarPedidoUseCase;

    @Mock
    private GetPedidoUseCase getPedidoUseCase;

    @InjectMocks
    private EntregaEntregueListener listener;

    @Captor
    private ArgumentCaptor<EntregarPedidoCommand> commandCaptor;

    @Test
    void deveChamarUseCaseQuandoReceberEvento() {
        UUID pedidoId = UUID.randomUUID();
        String entregaId = UUID.randomUUID().toString();

        EntregaEntregueEventDTO eventDTO = new EntregaEntregueEventDTO(entregaId, Instant.now());

        Pedido pedido = mock(Pedido.class);
        when(getPedidoUseCase.getByEntregaId(any(BuscarPedidoPeloEntregaIdQuery.class))).thenReturn(pedido);
        when(pedido.getId()).thenReturn(pedidoId);

        Consumer<EntregaEntregueEventDTO> consumer = listener.entregaEntregue();
        consumer.accept(eventDTO);

        verify(entregarPedidoUseCase, times(1)).entregar(commandCaptor.capture());

        EntregarPedidoCommand command = commandCaptor.getValue();
        assertEquals(pedidoId, command.getId());

        ArgumentCaptor<BuscarPedidoPeloEntregaIdQuery> queryCaptor = ArgumentCaptor.forClass(BuscarPedidoPeloEntregaIdQuery.class);
        verify(getPedidoUseCase, times(1)).getByEntregaId(queryCaptor.capture());
        assertEquals(entregaId, queryCaptor.getValue().getId());
    }
}
