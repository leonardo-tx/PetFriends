package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoAdicionarItemCommand;

import java.util.concurrent.CompletableFuture;

public interface AdicionarItemNoAlmoxarifadoUseCase {
    CompletableFuture<Void> adicionar(AlmoxarifadoAdicionarItemCommand command);
}
