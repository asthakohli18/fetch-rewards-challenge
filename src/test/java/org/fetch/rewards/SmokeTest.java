package org.fetch.rewards;

import static org.assertj.core.api.Assertions.assertThat;

import org.fetch.rewards.controller.RewardsController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

    @Autowired
    private RewardsController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}