package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.CriarEntregaCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CriarEntregaUseCase {
    CompletableFuture<UUID> criar(CriarEntregaCommand command);
}
