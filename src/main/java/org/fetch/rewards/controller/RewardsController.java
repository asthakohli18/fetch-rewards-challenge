package org.fetch.rewards.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.fetch.rewards.entity.Receipt;
import org.fetch.rewards.response.Points;
import org.fetch.rewards.response.ReceiptId;
import org.fetch.rewards.service.ReceiptProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class RewardsController {

    @Autowired
    ReceiptProcessorService rewardProcessingService;

    @PostMapping("/receipts/process")
    @ResponseBody
    public ResponseEntity<?> processReceipt(@Valid @RequestBody Receipt receipt) {
        try {
            String id = rewardProcessingService.processReceipt(receipt);
            return ResponseEntity.ok().body(ReceiptId.builder().id(id).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<?> getRewardPoints(@NotBlank @PathVariable String id) {
        try {
            Integer points = rewardProcessingService.getRewardPoints(id);
            if (Objects.isNull(points))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No receipt found for that id.");
            return ResponseEntity.ok().body(Points.builder().points(points).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
