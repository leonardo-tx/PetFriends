package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.CriarEntregaCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarEntregaServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private CriarEntregaService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        CriarEntregaCommand command = mock(CriarEntregaCommand.class);
        CompletableFuture<UUID> future = CompletableFuture.completedFuture(UUID.randomUUID());
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<UUID> resultado = service.criar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
