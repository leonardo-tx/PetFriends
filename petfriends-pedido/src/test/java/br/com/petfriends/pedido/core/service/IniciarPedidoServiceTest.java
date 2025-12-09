package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.IniciarPedidoCommand;
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
class IniciarPedidoServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private IniciarPedidoService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        IniciarPedidoCommand command = mock(IniciarPedidoCommand.class);
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<Void> resultado = service.iniciar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
