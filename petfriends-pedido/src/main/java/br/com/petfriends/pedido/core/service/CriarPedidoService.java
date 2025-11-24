package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.CriarPedidoCommand;
import br.com.petfriends.pedido.core.exception.PedidoNaoEncontradoException;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.in.CriarPedidoUseCase;
import br.com.petfriends.pedido.core.port.out.FindPedidoPort;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CriarPedidoService implements CriarPedidoUseCase {
    private final CommandGateway commandGateway;

    public CriarPedidoService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<UUID> criar(CriarPedidoCommand command) {
        return commandGateway.send(command);
    }
}
