package br.com.petfriends.transporte.core.command;

import java.util.UUID;

public final class IniciarEntregaCommand extends AggregateCommand<UUID> {
    public IniciarEntregaCommand(UUID id) {
        super(id);
    }
}
