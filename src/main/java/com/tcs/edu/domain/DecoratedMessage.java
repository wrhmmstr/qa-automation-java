package com.tcs.edu.domain;

import java.util.UUID;
import java.util.Objects;

public class DecoratedMessage extends Message{
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

    public void setLevel(Severity level){
        this.level=level;
    }

    public void setMessage(String message){
        this.message=message;
    }

//    public String getDecoratedBody(){
//        return decoratedBody;
//    }
//    public void setDecoratedBody(String decoratedBody){
//        this.decoratedBody = decoratedBody;
//    }

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
