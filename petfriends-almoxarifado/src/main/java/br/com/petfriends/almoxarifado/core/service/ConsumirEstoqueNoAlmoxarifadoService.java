package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoConsumirEstoqueCommand;
import br.com.petfriends.almoxarifado.core.port.in.ConsumirEstoqueNoAlmoxarifadoUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ConsumirEstoqueNoAlmoxarifadoService implements ConsumirEstoqueNoAlmoxarifadoUseCase {
    private final CommandGateway commandGateway;

    public ConsumirEstoqueNoAlmoxarifadoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> consumirEstoque(AlmoxarifadoConsumirEstoqueCommand command) {
        return commandGateway.send(command);
    }
}
