package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.CriarAlmoxarifadoCommand;
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
class CriarAlmoxarifadoServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private CriarAlmoxarifadoService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        CriarAlmoxarifadoCommand command = mock(CriarAlmoxarifadoCommand.class);
        CompletableFuture<UUID> future = CompletableFuture.completedFuture(UUID.randomUUID());
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<UUID> resultado = service.criar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
