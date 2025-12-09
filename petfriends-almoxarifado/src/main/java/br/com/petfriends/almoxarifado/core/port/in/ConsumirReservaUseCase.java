package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.ConsumirReservaCommand;

import java.util.concurrent.CompletableFuture;

public interface ConsumirReservaUseCase {
    CompletableFuture<Void> consumirReservas(ConsumirReservaCommand command);
}
