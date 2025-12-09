package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.CriarEntregaCommand;
import br.com.petfriends.transporte.core.port.in.CriarEntregaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CriarEntregaService implements CriarEntregaUseCase {
    private final CommandGateway commandGateway;

    public CriarEntregaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<UUID> criar(CriarEntregaCommand command) {
        return commandGateway.send(command);
    }
}
