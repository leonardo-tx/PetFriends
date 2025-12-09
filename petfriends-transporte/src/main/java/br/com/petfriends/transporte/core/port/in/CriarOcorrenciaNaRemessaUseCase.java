package br.com.petfriends.transporte.core.port.in;

import br.com.petfriends.transporte.core.command.CriarOcorrenciaRemessaCommand;

import java.util.concurrent.CompletableFuture;

public interface CriarOcorrenciaNaRemessaUseCase {
    CompletableFuture<Void> criarOcorrencia(CriarOcorrenciaRemessaCommand command);
}
