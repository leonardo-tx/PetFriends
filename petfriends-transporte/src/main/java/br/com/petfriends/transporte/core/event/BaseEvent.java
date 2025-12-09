package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEvent<?> baseEvent = (BaseEvent<?>)o;
        return Objects.equals(id, baseEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
