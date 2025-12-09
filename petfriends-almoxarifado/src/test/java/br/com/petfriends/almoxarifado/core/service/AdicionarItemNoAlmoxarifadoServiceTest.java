package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoAdicionarItemCommand;
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
class AdicionarItemNoAlmoxarifadoServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private AdicionarItemNoAlmoxarifadoService service;

    @Test
    void deveEnviarComandoParaOGateway() {
        AlmoxarifadoAdicionarItemCommand command = mock(AlmoxarifadoAdicionarItemCommand.class);
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<Void> resultado = service.adicionar(command);

        verify(commandGateway, times(1)).send(command);
        assertSame(future, resultado);
    }
}
