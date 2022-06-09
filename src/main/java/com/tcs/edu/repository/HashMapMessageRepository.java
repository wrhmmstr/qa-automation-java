package com.tcs.edu.repository;

import com.tcs.edu.domain.DecoratedMessage;
import com.tcs.edu.domain.Message;
import com.tcs.edu.domain.Severity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HashMapMessageRepository implements MessageRepository {
    private Map<UUID, DecoratedMessage> messages = new HashMap<>();

    @Override
    public UUID create(DecoratedMessage message) {
        UUID uuid = UUID.randomUUID();
        message.setId(uuid);
        messages.put(uuid, message);
        return uuid;
    }

    @Override
    public DecoratedMessage findByPrimaryKey(UUID key) {
        return messages.get(key);
    }

    @Override
    public Collection<DecoratedMessage> findAll() {
        return messages.values();
    }

    @Override
    public Collection<DecoratedMessage> findBySeverity(Severity by) {
        return messages.values().stream().filter(c -> c.getLevel() == by).collect(Collectors.toList());
    }


}
