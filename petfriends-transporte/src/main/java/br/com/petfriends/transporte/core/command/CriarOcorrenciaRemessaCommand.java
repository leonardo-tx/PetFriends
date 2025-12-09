package br.com.petfriends.transporte.core.command;

import java.util.UUID;

public final class CriarOcorrenciaRemessaCommand extends AggregateCommand<UUID> {
    private final String almoxarifadoId;

    public CriarOcorrenciaRemessaCommand(UUID id, String almoxarifadoId) {
        super(id);
        this.almoxarifadoId = almoxarifadoId;
    }

    public String getAlmoxarifadoId() {
        return almoxarifadoId;
    }
}
