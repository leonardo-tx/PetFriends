package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.SepararRemessaCommand;

import java.util.concurrent.CompletableFuture;

public interface SepararRemessaUseCase {
    CompletableFuture<Void> separar(SepararRemessaCommand command);
}
