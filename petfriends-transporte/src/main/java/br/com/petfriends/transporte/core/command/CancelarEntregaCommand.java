package br.com.petfriends.transporte.core.command;

import java.util.UUID;

public final class CancelarEntregaCommand extends AggregateCommand<UUID> {
    public CancelarEntregaCommand(UUID id) {
        super(id);
    }
}
