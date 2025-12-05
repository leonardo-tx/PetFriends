package br.com.petfriends.almoxarifado.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class AggregateCommand<T> extends BaseCommand {
    @TargetAggregateIdentifier
    private final T id;

    protected AggregateCommand(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
