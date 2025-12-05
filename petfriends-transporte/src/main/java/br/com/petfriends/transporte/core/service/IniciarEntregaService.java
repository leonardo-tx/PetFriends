package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.IniciarEntregaCommand;
import br.com.petfriends.transporte.core.port.in.IniciarEntregaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class IniciarEntregaService implements IniciarEntregaUseCase {
    private final CommandGateway commandGateway;

    public IniciarEntregaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> iniciar(IniciarEntregaCommand command) {
        return commandGateway.send(command);
    }
}
