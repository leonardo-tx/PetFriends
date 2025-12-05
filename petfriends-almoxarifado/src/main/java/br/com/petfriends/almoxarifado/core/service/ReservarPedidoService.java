package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoLiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoReservarItemCommand;
import br.com.petfriends.almoxarifado.core.command.ReservarItemCommand;
import br.com.petfriends.almoxarifado.core.command.ReservarPedidoCommand;
import br.com.petfriends.almoxarifado.core.event.PedidoReservaConcluidoEvent;
import br.com.petfriends.almoxarifado.core.event.PedidoReservaFalhouEvent;
import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNaoEncontradoException;
import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoReserva;
import br.com.petfriends.almoxarifado.core.port.in.ReservarPedidoUseCase;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ReservarPedidoService implements ReservarPedidoUseCase {
    private final CommandGateway commandGateway;
    private final EventGateway eventGateway;
    private final FindAlmoxarifadoPort findAlmoxarifadoPort;

    public ReservarPedidoService(
            CommandGateway commandGateway,
            EventGateway eventGateway,
            FindAlmoxarifadoPort findAlmoxarifadoPort
    ) {
        this.commandGateway = commandGateway;
        this.eventGateway = eventGateway;
        this.findAlmoxarifadoPort = findAlmoxarifadoPort;
    }

    @Override
    public CompletableFuture<List<AlmoxarifadoReserva>> reservar(ReservarPedidoCommand command) {
        List<UUID> almoxarifadoIds = command.getReservarItemCommands()
                .stream()
                .map(c -> findAlmoxarifadoPort.findFirstByItemDisponivel(
                        c.getItemId(),
                        c.getQuantidade()
                ).orElseThrow(() -> {
                    publicarFalha(command.getPedidoId());
                    return new AlmoxarifadoNaoEncontradoException();
                }).getId())
                .toList();

        int sucessos = 0;
        try {
            for (int i = 0; i < almoxarifadoIds.size(); i++) {
                ReservarItemCommand reservarItemCommand = command.getReservarItemCommands().get(i);
                AlmoxarifadoReservarItemCommand almoxarifadoReservarItemCommand = new AlmoxarifadoReservarItemCommand(
                        almoxarifadoIds.get(i),
                        command.getPedidoId(),
                        reservarItemCommand.getItemId(),
                        reservarItemCommand.getQuantidade()
                );
                commandGateway.sendAndWait(almoxarifadoReservarItemCommand);
                sucessos++;
            }
        } catch (Exception e) {
            fazerRollbackDeReservas(command.getPedidoId(), almoxarifadoIds, sucessos);
            publicarFalha(command.getPedidoId());
            throw e;
        }
        PedidoReservaConcluidoEvent eventoConcluido = new PedidoReservaConcluidoEvent(
                command.getPedidoId(),
                Instant.now(),
                new ArrayList<>()
        );
        for (int i = 0; i < almoxarifadoIds.size(); i++) {
            ReservarItemCommand reservarItemCommand = command.getReservarItemCommands().get(i);
            AlmoxarifadoReserva almoxarifadoReserva = new AlmoxarifadoReserva(
                    almoxarifadoIds.get(i),
                    reservarItemCommand.getItemId(),
                    reservarItemCommand.getQuantidade()
            );
            eventoConcluido.getAlmoxarifadoReservas().add(almoxarifadoReserva);
        }
        eventGateway.publish(eventoConcluido);
        return CompletableFuture.completedFuture(eventoConcluido.getAlmoxarifadoReservas());
    }

    private void fazerRollbackDeReservas(
            String pedidoId,
            List<UUID> almoxarifadoIds,
            int sucessos
    ) {
        for (int i = 0; i < sucessos; i++) {
            AlmoxarifadoLiberarReservaCommand command = new AlmoxarifadoLiberarReservaCommand(
                    almoxarifadoIds.get(i),
                    pedidoId
            );
            commandGateway.send(command);
        }
    }

    private void publicarFalha(String pedidoId) {
        PedidoReservaFalhouEvent event = new PedidoReservaFalhouEvent(pedidoId, Instant.now());
        eventGateway.publish(event);
    }
}
