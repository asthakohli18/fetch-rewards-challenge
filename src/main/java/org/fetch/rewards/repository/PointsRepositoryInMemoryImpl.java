package org.fetch.rewards.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public final class PointsRepositoryInMemoryImpl implements PointsRepository {

    private final Map<String, Integer> receiptPointsMap = new ConcurrentHashMap<>();

    @Override
    public void saveRewards(String id, Integer points) {
        receiptPointsMap.put(id, points);
    }

    @Override
    public Integer getRewards(String id) {
        return receiptPointsMap.get(id);
    }
}
