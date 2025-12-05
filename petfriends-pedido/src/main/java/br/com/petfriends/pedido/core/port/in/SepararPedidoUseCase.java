package br.com.petfriends.pedido.core.port.in;

import br.com.petfriends.pedido.core.command.SepararPedidoCommand;

import java.util.concurrent.CompletableFuture;

public interface SepararPedidoUseCase {
    CompletableFuture<Void> separar(SepararPedidoCommand command);
}
