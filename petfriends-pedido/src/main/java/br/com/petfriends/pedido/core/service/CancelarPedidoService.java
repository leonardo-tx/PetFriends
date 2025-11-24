package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.CancelarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.CancelarPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CancelarPedidoService implements CancelarPedidoUseCase {
    private final CommandGateway commandGateway;

    public CancelarPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> cancelar(CancelarPedidoCommand command) {
        return commandGateway.send(command);
    }
}
