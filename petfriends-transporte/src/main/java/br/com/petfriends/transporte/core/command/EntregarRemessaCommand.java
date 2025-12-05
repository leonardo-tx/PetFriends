package br.com.petfriends.transporte.core.command;

import java.util.UUID;

public final class EntregarRemessaCommand extends AggregateCommand<UUID> {
    private final String almoxarifadoId;

    public EntregarRemessaCommand(UUID id, String almoxarifadoId) {
        super(id);
        this.almoxarifadoId = almoxarifadoId;
    }

    public String getAlmoxarifadoId() {
        return almoxarifadoId;
    }
}
