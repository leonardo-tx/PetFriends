package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.IniciarEntregaCommand;
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
class IniciarEntregaServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private IniciarEntregaService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        IniciarEntregaCommand command = mock(IniciarEntregaCommand.class);
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<Void> resultado = service.iniciar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
