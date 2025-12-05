package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.CriarAlmoxarifadoCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CriarAlmoxarifadoUseCase {
    CompletableFuture<UUID> criar(CriarAlmoxarifadoCommand command);
}
