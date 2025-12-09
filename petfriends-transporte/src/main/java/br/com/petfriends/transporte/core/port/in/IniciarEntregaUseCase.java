package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.IniciarEntregaCommand;

import java.util.concurrent.CompletableFuture;

public interface IniciarEntregaUseCase {
    CompletableFuture<Void> iniciar(IniciarEntregaCommand command);
}
