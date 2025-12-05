package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.ReservarItemCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ReservarItemUseCase {
    CompletableFuture<UUID> reservar(ReservarItemCommand command);
}
