package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoAdicionarItemCommand;
import br.com.petfriends.almoxarifado.core.port.in.AdicionarItemNoAlmoxarifadoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AdicionarItemNoAlmoxarifadoService implements AdicionarItemNoAlmoxarifadoUseCase {
    private final CommandGateway commandGateway;

    public AdicionarItemNoAlmoxarifadoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> adicionar(AlmoxarifadoAdicionarItemCommand command) {
        return commandGateway.send(command);
    }
}
