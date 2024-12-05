package org.fetch.rewards.service;

import org.fetch.rewards.entity.Receipt;
import org.fetch.rewards.repository.PointsRepository;
import org.fetch.rewards.utils.ReceiptProcessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class ReceiptProcessorService {
    @Autowired
    PointsRepository pointsRepository;

    public String processReceipt(final Receipt receipt) {
        int points = ReceiptProcessorUtils.pointsFromRetailerField(receipt.getRetailer()) + ReceiptProcessorUtils.pointsFromTotalField(receipt.getTotal()) + ReceiptProcessorUtils.pointsFromItems(receipt.getItems()) + ReceiptProcessorUtils.pointsFromPurchaseDate(receipt.getPurchaseDate()) + ReceiptProcessorUtils.pointsFromPurchaseTime(receipt.getPurchaseTime());
        String id = UUID.randomUUID().toString();
        pointsRepository.saveRewards(id, points);
        return id;
    }

    public Integer getRewardPoints(String id) {
        return pointsRepository.getRewards(id);
    }
}
