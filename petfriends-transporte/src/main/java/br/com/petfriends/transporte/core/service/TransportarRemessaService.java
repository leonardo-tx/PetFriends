package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.TransportarRemessaCommand;
import br.com.petfriends.transporte.core.port.in.TransportarRemessaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransportarRemessaService implements TransportarRemessaUseCase {
    private final CommandGateway commandGateway;

    public TransportarRemessaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> transportar(TransportarRemessaCommand command) {
        return commandGateway.send(command);
    }
}
