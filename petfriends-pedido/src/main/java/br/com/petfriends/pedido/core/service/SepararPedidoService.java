package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.SepararPedidoCommand;
import br.com.petfriends.pedido.core.port.in.SepararPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SepararPedidoService implements SepararPedidoUseCase {
    private final CommandGateway commandGateway;

    public SepararPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> separar(SepararPedidoCommand command) {
        return commandGateway.send(command);
    }
}
