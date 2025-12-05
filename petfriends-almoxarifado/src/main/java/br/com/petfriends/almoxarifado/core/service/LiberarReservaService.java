package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoLiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.command.LiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.in.LiberarReservaUseCase;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class LiberarReservaService implements LiberarReservaUseCase {
    private final CommandGateway commandGateway;
    private final FindAlmoxarifadoPort findAlmoxarifadoPort;

    public LiberarReservaService(
            CommandGateway commandGateway,
            FindAlmoxarifadoPort findAlmoxarifadoPort
    ) {
        this.commandGateway = commandGateway;
        this.findAlmoxarifadoPort = findAlmoxarifadoPort;
    }

    @Override
    public CompletableFuture<Void> liberarReserva(LiberarReservaCommand command) {
        List<UUID> almoxarifadoIds = findAlmoxarifadoPort.findByPedidoIdInReservas(command.getPedidoId())
                .stream()
                .map(Almoxarifado::getId)
                .toList();
        List<CompletableFuture<Object>> futures = almoxarifadoIds.stream()
                .map(almoxarifadoId -> {
                    AlmoxarifadoLiberarReservaCommand newCommand = new AlmoxarifadoLiberarReservaCommand(
                            almoxarifadoId,
                            command.getPedidoId()
                    );
                    return commandGateway.send(newCommand);
                })
                .toList();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
}
