package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoLiberarReservaCommand;

import java.util.concurrent.CompletableFuture;

public interface LiberarReservaNoAlmoxarifadoUseCase {
    CompletableFuture<Void> liberarReserva(AlmoxarifadoLiberarReservaCommand command);
}
