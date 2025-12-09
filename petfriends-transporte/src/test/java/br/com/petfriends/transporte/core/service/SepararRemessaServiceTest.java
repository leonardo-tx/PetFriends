package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.SepararRemessaCommand;
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
class SepararRemessaServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private SepararRemessaService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        SepararRemessaCommand command = mock(SepararRemessaCommand.class);
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<Void> resultado = service.separar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
