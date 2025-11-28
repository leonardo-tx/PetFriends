package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.CriarEntregaCommand;
import br.com.petfriends.transporte.core.exception.CEPNaoExisteException;
import br.com.petfriends.transporte.core.model.CEP;
import br.com.petfriends.transporte.core.port.in.CriarEntregaUseCase;
import br.com.petfriends.transporte.core.port.out.ValidarCepPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CriarEntregaService implements CriarEntregaUseCase {
    private final CommandGateway commandGateway;
    private final ValidarCepPort validarCepPort;

    public CriarEntregaService(CommandGateway commandGateway, ValidarCepPort validarCepPort) {
        this.commandGateway = commandGateway;
        this.validarCepPort = validarCepPort;
    }

    @Override
    public CompletableFuture<UUID> criar(CriarEntregaCommand command) {
        CEP cep = CEP.valueOf(command.getCep());
        return validarCepPort.cepExiste(cep).flatMap(existe -> {
            if (existe) {
                return Mono.fromFuture(commandGateway.<UUID>send(command));
            }
            return Mono.error(new CEPNaoExisteException());
        }).toFuture();
    }
}
