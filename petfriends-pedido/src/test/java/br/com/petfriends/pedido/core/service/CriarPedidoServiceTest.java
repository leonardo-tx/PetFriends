package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.CriarPedidoCommand;
import br.com.petfriends.pedido.core.exception.CEPNaoExisteException;
import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.port.out.ValidarCepPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarPedidoServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @Mock
    private ValidarCepPort validarCepPort;

    @InjectMocks
    private CriarPedidoService service;

    @Test
    void deveCriarPedidoQuandoCepExiste() {
        CriarPedidoCommand command = mock(CriarPedidoCommand.class);
        when(command.getCep()).thenReturn("12345678");

        CEP cep = new CEP("12345678");

        when(validarCepPort.cepExiste(cep)).thenReturn(Mono.just(true));

        UUID pedidoId = UUID.randomUUID();
        CompletableFuture<UUID> future = CompletableFuture.completedFuture(pedidoId);

        when(commandGateway.send(command)).thenReturn((CompletableFuture)future);

        CompletableFuture<UUID> resultado = service.criar(command);

        assertEquals(pedidoId, resultado.join());
        verify(validarCepPort, times(1)).cepExiste(cep);
        verify(commandGateway, times(1)).send(command);
    }

    @Test
    void deveLancarExcecaoQuandoCepNaoExiste() {
        CriarPedidoCommand command = mock(CriarPedidoCommand.class);
        when(command.getCep()).thenReturn("87654321");

        CEP cep = new CEP("87654321");

        when(validarCepPort.cepExiste(cep)).thenReturn(Mono.just(false));

        CompletableFuture<UUID> futuro = service.criar(command);

        CompletionException ex = assertThrows(CompletionException.class, futuro::join);
        assertInstanceOf(CEPNaoExisteException.class, ex.getCause());

        verify(validarCepPort, times(1)).cepExiste(cep);
        verify(commandGateway, never()).send(command);
    }
}
