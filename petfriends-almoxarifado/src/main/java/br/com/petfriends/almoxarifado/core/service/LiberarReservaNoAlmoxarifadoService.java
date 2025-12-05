package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoLiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.port.in.LiberarReservaNoAlmoxarifadoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class LiberarReservaNoAlmoxarifadoService implements LiberarReservaNoAlmoxarifadoUseCase {
    private final CommandGateway commandGateway;

    public LiberarReservaNoAlmoxarifadoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> liberarReserva(AlmoxarifadoLiberarReservaCommand command) {
        return commandGateway.send(command);
    }
}
