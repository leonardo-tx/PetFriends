package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoReservarItemCommand;
import br.com.petfriends.almoxarifado.core.command.ReservarItemCommand;
import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNaoEncontradoException;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.in.ReservarItemUseCase;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ReservarItemService implements ReservarItemUseCase {
    private final CommandGateway commandGateway;
    private final FindAlmoxarifadoPort findAlmoxarifadoPort;

    public ReservarItemService(CommandGateway commandGateway, FindAlmoxarifadoPort findAlmoxarifadoPort) {
        this.commandGateway = commandGateway;
        this.findAlmoxarifadoPort = findAlmoxarifadoPort;
    }

    @Override
    public CompletableFuture<UUID> reservar(ReservarItemCommand command) {
        Almoxarifado almoxarifado = findAlmoxarifadoPort.findFirstByItemDisponivel(
                command.getItemId(),
                command.getQuantidade()
        ).orElseThrow(AlmoxarifadoNaoEncontradoException::new);
        AlmoxarifadoReservarItemCommand novoCommand = new AlmoxarifadoReservarItemCommand(
                almoxarifado.getId(),
                command.getItemId(),
                command.getQuantidade()
        );
        return commandGateway.send(novoCommand).thenApply(o -> almoxarifado.getId());
    }
}
