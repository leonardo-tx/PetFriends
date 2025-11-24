package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.CriarPedidoCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CriarPedidoUseCase {
    CompletableFuture<UUID> criar(CriarPedidoCommand command);
}
