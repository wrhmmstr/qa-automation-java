package com.tcs.edu.repository;

import com.tcs.edu.domain.DecoratedMessage;
import com.tcs.edu.domain.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapMessageRepository implements MessageRepository {
    private Map<UUID, DecoratedMessage> messages = new HashMap<>();

    @Override
    public UUID create(DecoratedMessage message) {
        UUID uuid = UUID.randomUUID();
        messages.put(uuid, message);
        return uuid;
    }

    @Override
    public DecoratedMessage findByPrimaryKey(String key) {
        return messages.get(key);
    }

    @Override
    public Collection<DecoratedMessage> findAll() {
        return messages.values();
    }


}
