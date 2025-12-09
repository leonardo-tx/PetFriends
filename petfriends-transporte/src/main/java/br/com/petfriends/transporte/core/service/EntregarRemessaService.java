package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.EntregarRemessaCommand;
import br.com.petfriends.transporte.core.port.in.EntregarRemessaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EntregarRemessaService implements EntregarRemessaUseCase {
    private final CommandGateway commandGateway;

    public EntregarRemessaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> entregar(EntregarRemessaCommand command) {
        return commandGateway.send(command);
    }
}
