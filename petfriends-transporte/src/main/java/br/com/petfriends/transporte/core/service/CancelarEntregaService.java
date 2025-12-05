package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.CancelarEntregaCommand;
import br.com.petfriends.transporte.core.port.in.CancelarEntregaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CancelarEntregaService implements CancelarEntregaUseCase {
    private final CommandGateway commandGateway;

    public CancelarEntregaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> cancelar(CancelarEntregaCommand command) {
        return commandGateway.send(command);
    }
}
