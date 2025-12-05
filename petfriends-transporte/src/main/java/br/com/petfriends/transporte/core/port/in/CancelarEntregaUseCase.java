package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.CancelarEntregaCommand;

import java.util.concurrent.CompletableFuture;

public interface CancelarEntregaUseCase {
    CompletableFuture<Void> cancelar(CancelarEntregaCommand command);
}
