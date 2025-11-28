package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.PagarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.PagarPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PagarPedidoService implements PagarPedidoUseCase {
    private final CommandGateway commandGateway;

    public PagarPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> pagar(PagarPedidoCommand command) {
        return commandGateway.send(command);
    }
}
