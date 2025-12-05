package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.EntregarRemessaCommand;

import java.util.concurrent.CompletableFuture;

public interface EntregarRemessaUseCase {
    CompletableFuture<Void> entregar(EntregarRemessaCommand command);
}
