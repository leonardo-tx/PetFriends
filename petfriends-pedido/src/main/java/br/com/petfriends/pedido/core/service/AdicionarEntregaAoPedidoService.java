package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.AdicionarEntregaAoPedidoCommand;
import br.com.petfriends.pedido.core.port.in.AdicionarEntregaAoPedidoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AdicionarEntregaAoPedidoService implements AdicionarEntregaAoPedidoUseCase {
    private final CommandGateway commandGateway;

    public AdicionarEntregaAoPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> adicionarEntrega(AdicionarEntregaAoPedidoCommand command) {
        return commandGateway.send(command);
    }
}
