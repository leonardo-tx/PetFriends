package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.EntregarPedidoCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntregarPedidoServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private EntregarPedidoService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        EntregarPedidoCommand command = mock(EntregarPedidoCommand.class);
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<Void> resultado = service.entregar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
