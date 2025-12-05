package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoConsumirEstoqueCommand;
import br.com.petfriends.almoxarifado.core.command.ConsumirReservaCommand;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.in.ConsumirReservaUseCase;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ConsumirReservaService implements ConsumirReservaUseCase {
    private final CommandGateway commandGateway;
    private final FindAlmoxarifadoPort findAlmoxarifadoPort;

    public ConsumirReservaService(CommandGateway commandGateway, FindAlmoxarifadoPort findAlmoxarifadoPort) {
        this.commandGateway = commandGateway;
        this.findAlmoxarifadoPort = findAlmoxarifadoPort;
    }

    @Override
    public CompletableFuture<Void> consumirReservas(ConsumirReservaCommand command) {
        List<UUID> almoxarifadoIds = findAlmoxarifadoPort.findByPedidoIdInReservas(command.getPedidoId())
                .stream()
                .map(Almoxarifado::getId)
                .toList();
        List<CompletableFuture<Object>> futures = almoxarifadoIds.stream()
                .map(almoxarifadoId -> {
                    AlmoxarifadoConsumirEstoqueCommand newCommand = new AlmoxarifadoConsumirEstoqueCommand(
                            almoxarifadoId,
                            command.getPedidoId()
                    );
                    return commandGateway.send(newCommand);
                })
                .toList();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
}
