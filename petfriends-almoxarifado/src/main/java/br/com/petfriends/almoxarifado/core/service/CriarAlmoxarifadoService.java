package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.CriarAlmoxarifadoCommand;
import br.com.petfriends.almoxarifado.core.port.in.CriarAlmoxarifadoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CriarAlmoxarifadoService implements CriarAlmoxarifadoUseCase {
    private final CommandGateway commandGateway;

    public CriarAlmoxarifadoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<UUID> criar(CriarAlmoxarifadoCommand command) {
        return commandGateway.send(command);
    }
}
