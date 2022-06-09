package com.tcs.edu.repository;

import com.tcs.edu.domain.DecoratedMessage;
import com.tcs.edu.domain.Severity;
//import com.tcs.edu.domain.Message;

import java.util.Collection;
import java.util.UUID;

public interface MessageRepository {
    UUID create(DecoratedMessage message);
    DecoratedMessage findByPrimaryKey(UUID key);
    Collection<DecoratedMessage> findAll();
    Collection<DecoratedMessage> findBySeverity(Severity by);
}
