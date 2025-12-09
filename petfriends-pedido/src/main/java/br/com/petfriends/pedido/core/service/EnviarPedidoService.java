package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.EnviarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.EnviarPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EnviarPedidoService implements EnviarPedidoUseCase {
    private final CommandGateway commandGateway;

    public EnviarPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> enviar(EnviarPedidoCommand command) {
        return commandGateway.send(command);
    }
}
