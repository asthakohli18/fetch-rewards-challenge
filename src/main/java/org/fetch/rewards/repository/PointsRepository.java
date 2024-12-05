package org.fetch.rewards.repository;

public interface PointsRepository {

    public void saveRewards(String id, Integer points);

    public Integer getRewards(String id);

}
