package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.SepararRemessaCommand;
import br.com.petfriends.transporte.core.port.in.SepararRemessaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SepararRemessaService implements SepararRemessaUseCase {
    private final CommandGateway commandGateway;

    public SepararRemessaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> separar(SepararRemessaCommand command) {
        return commandGateway.send(command);
    }
}
