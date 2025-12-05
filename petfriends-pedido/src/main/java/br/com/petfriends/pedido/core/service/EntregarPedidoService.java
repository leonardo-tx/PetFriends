package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.EntregarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.EntregarPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EntregarPedidoService implements EntregarPedidoUseCase {
    private final CommandGateway commandGateway;

    public EntregarPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> entregar(EntregarPedidoCommand command) {
        return commandGateway.send(command);
    }
}
