package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.command.CriarOcorrenciaRemessaCommand;
import br.com.petfriends.transporte.core.port.in.CriarOcorrenciaNaRemessaUseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CriarOcorrenciaNaRemessaService implements CriarOcorrenciaNaRemessaUseCase {
    private final CommandGateway commandGateway;

    public CriarOcorrenciaNaRemessaService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> criarOcorrencia(CriarOcorrenciaRemessaCommand command) {
        return commandGateway.send(command);
    }
}
