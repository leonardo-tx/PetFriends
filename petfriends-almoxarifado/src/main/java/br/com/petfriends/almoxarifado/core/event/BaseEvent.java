package br.com.petfriends.almoxarifado.core.event;

import java.time.Instant;

public abstract class BaseEvent<T> {
    private final T id;
    private final Instant timestamp;

    protected BaseEvent(T id, Instant timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public T getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
