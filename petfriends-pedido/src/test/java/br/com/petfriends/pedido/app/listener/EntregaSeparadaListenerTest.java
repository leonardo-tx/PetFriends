package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaSeparadaEventDTO;
import br.com.petfriends.pedido.core.command.SepararPedidoCommand;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.in.SepararPedidoUseCase;
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
class EntregaSeparadaListenerTest {
    @Mock
    private SepararPedidoUseCase separarPedidoUseCase;

    @Mock
    private GetPedidoUseCase getPedidoUseCase;

    @InjectMocks
    private EntregaSeparadaListener listener;

    @Captor
    private ArgumentCaptor<SepararPedidoCommand> commandCaptor;

    @Test
    void deveChamarUseCaseQuandoReceberEvento() {
        UUID pedidoId = UUID.randomUUID();
        String entregaId = UUID.randomUUID().toString();

        EntregaSeparadaEventDTO eventDTO = new EntregaSeparadaEventDTO(entregaId, Instant.now());

        Pedido pedido = mock(Pedido.class);
        when(getPedidoUseCase.getByEntregaId(any(BuscarPedidoPeloEntregaIdQuery.class))).thenReturn(pedido);
        when(pedido.getId()).thenReturn(pedidoId);

        Consumer<EntregaSeparadaEventDTO> consumer = listener.entregaSeparada();
        consumer.accept(eventDTO);

        verify(separarPedidoUseCase, times(1)).separar(commandCaptor.capture());

        SepararPedidoCommand command = commandCaptor.getValue();
        assertEquals(pedidoId, command.getId());

        ArgumentCaptor<BuscarPedidoPeloEntregaIdQuery> queryCaptor = ArgumentCaptor.forClass(BuscarPedidoPeloEntregaIdQuery.class);
        verify(getPedidoUseCase, times(1)).getByEntregaId(queryCaptor.capture());
        assertEquals(entregaId, queryCaptor.getValue().getId());
    }
}
