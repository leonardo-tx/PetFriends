package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaCriadaEventDTO;
import br.com.petfriends.pedido.core.command.AdicionarEntregaAoPedidoCommand;
import br.com.petfriends.pedido.core.port.in.AdicionarEntregaAoPedidoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntregaCriadaListenerTest {
    @Mock
    private AdicionarEntregaAoPedidoUseCase useCase;

    @InjectMocks
    private EntregaCriadaListener listener;

    @Captor
    private ArgumentCaptor<AdicionarEntregaAoPedidoCommand> commandCaptor;

    @Test
    void deveChamarUseCaseQuandoReceberEvento() {
        UUID pedidoId = UUID.randomUUID();
        String entregaId = UUID.randomUUID().toString();
        EntregaCriadaEventDTO eventDTO = new EntregaCriadaEventDTO(entregaId, pedidoId, Instant.now());

        when(useCase.adicionarEntrega(any())).thenReturn(CompletableFuture.completedFuture(null));

        Consumer<EntregaCriadaEventDTO> consumer = listener.entregaCriada();
        consumer.accept(eventDTO);

        verify(useCase, times(1)).adicionarEntrega(commandCaptor.capture());

        AdicionarEntregaAoPedidoCommand command = commandCaptor.getValue();
        assertEquals(entregaId, command.getEntregaId());
        assertEquals(pedidoId, command.getId());
    }
}
