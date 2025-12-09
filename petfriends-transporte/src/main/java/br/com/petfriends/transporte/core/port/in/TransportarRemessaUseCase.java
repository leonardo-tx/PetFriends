package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.TransportarRemessaCommand;

import java.util.concurrent.CompletableFuture;

public interface TransportarRemessaUseCase {
    CompletableFuture<Void> transportar(TransportarRemessaCommand command);
}
