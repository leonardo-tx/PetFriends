package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.IniciarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.IniciarPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class IniciarPedidoService implements IniciarPedidoUseCase {
    private final CommandGateway commandGateway;

    public IniciarPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> iniciar(IniciarPedidoCommand command) {
        return commandGateway.send(command);
    }
}
