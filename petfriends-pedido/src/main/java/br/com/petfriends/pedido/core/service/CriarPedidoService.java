package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.command.CriarPedidoCommand;
import br.com.petfriends.pedido.core.exception.CEPNaoExisteException;
import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.port.in.CriarPedidoUseCase;
import br.com.petfriends.pedido.core.port.out.ValidarCepPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CriarPedidoService implements CriarPedidoUseCase {
    private final CommandGateway commandGateway;
    private final ValidarCepPort validarCepPort;

    public CriarPedidoService(CommandGateway commandGateway, ValidarCepPort validarCepPort) {
        this.commandGateway = commandGateway;
        this.validarCepPort = validarCepPort;
    }

    @Override
    public CompletableFuture<UUID> criar(CriarPedidoCommand command) {
        CEP cep = CEP.valueOf(command.getCep());
        return validarCepPort.cepExiste(cep).flatMap(existe -> {
            if (existe) {
                return Mono.fromFuture(commandGateway.<UUID>send(command));
            }
            return Mono.error(new CEPNaoExisteException());
        }).toFuture();
    }
}
