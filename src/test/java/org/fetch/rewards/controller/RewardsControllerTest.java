package org.fetch.rewards.controller;

import org.fetch.rewards.entity.Receipt;
import org.fetch.rewards.service.ReceiptProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = RewardsController.class)
class RewardsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptProcessorService receiptProcessorService;

    @Test
    public void testProcessReceipt() {
        String payload = "{\"retailer\":\"Target\",\"purchaseDate\":\"2022-01-01\",\"purchaseTime\":\"13:01\",\"items\":[{\"shortDescription\":\"MountainDew12PK\",\"price\":\"6.49\"},{\"shortDescription\":\"EmilsCheesePizza\",\"price\":\"12.25\"},{\"shortDescription\":\"KnorrCreamyChicken\",\"price\":\"1.26\"},{\"shortDescription\":\"DoritosNachoCheese\",\"price\":\"3.35\"},{\"shortDescription\":\"Klarbrunn12-PK12FLOZ\",\"price\":\"12.00\"}],\"total\":\"35.35\"}";
        when(receiptProcessorService.processReceipt(any(Receipt.class))).thenReturn("123-UUID");
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("123-UUID"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testProcessReceiptBadRequest() {
        when(receiptProcessorService.processReceipt(any(Receipt.class))).thenReturn("123-UUID");
        String payload = "{\"retailer\":\"!@#\",\"purchaseDate\":\"2022-01-01\",\"purchaseTime\":\"3:01\",\"items\":[],\"total\":\"ABCD\"}";
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.items").value("must not be empty"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseTime").value("Time must be in HH:MM format"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.retailer").value("must match \"^[\\w\\s\\-&]+$\""))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.total").value("must match \"^\\d+\\.\\d{2}$\""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetRewardPoints() {
        when(receiptProcessorService.getRewardPoints(any(String.class))).thenReturn(38);
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/receipts/123-UUID/points")
                            .content("123-UUID")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.points").value("38"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetRewardPointsWrongId() {
        when(receiptProcessorService.getRewardPoints("123UUID")).thenReturn(38);
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/receipts/badid/points")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.points").value("0"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}