package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.LiberarReservaCommand;

import java.util.concurrent.CompletableFuture;

public interface LiberarReservaUseCase {
    CompletableFuture<Void> liberarReserva(LiberarReservaCommand command);
}
