package br.com.petfriends.transporte.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseIdentifiableCommand<T> extends BaseCommand {
    @TargetAggregateIdentifier
    private final T id;

    protected BaseIdentifiableCommand(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
