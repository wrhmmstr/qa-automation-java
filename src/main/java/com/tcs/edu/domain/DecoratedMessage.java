package com.tcs.edu.domain;

import java.util.UUID;
import java.util.Objects;

public class DecoratedMessage extends Message{
    public static DecoratedMessage NOT_FOUND = new DecoratedMessage(null, "DecoratedMessage Not Found", null);
    public UUID id;

    public DecoratedMessage(Severity level, String message) {
        super(level, message);
    }

    public DecoratedMessage(Severity level, String message, UUID id) {
        super(level, message);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DecoratedMessage that = (DecoratedMessage) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
