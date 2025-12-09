package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.CancelarEntregaCommand;
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
class CancelarEntregaServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private CancelarEntregaService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        CancelarEntregaCommand command = mock(CancelarEntregaCommand.class);
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<Void> resultado = service.cancelar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
