package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoConsumirEstoqueCommand;

import java.util.concurrent.CompletableFuture;

public interface ConsumirEstoqueNoAlmoxarifadoUseCase {
    CompletableFuture<Void> consumirEstoque(AlmoxarifadoConsumirEstoqueCommand command);
}
