package org.fetch.rewards.utils;

import org.fetch.rewards.entity.Item;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReceiptProcessorUtils {

    public static int pointsFromPurchaseTime(final String purchaseTime) {
        LocalTime purTime = LocalTime.parse(purchaseTime);
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        if (purTime.isAfter(startTime) && purTime.isBefore(endTime))
            return 10;
        return 0;
    }

    public static int pointsFromRetailerField(final String retailer) {
        int points = 0;
        for (char ch : retailer.toCharArray())
            if (Character.isLetterOrDigit(ch))
                ++points;
        return points;
    }

    public static int pointsFromTotalField(final String total) {
        int points = 0;
        double tot = Double.parseDouble(total);
        if (Math.floor(tot) == tot)
            points += 50;
        if (tot % .25 == 0)
            points += 25;
        return points;
    }

    public static int pointsFromItems(final List<Item> items) {
        int points = 0;
        points += (items.size() / 2) * 5;
        for (Item item : items) {
            if (item.getShortDescription().trim().length() % 3 == 0)
                points += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
        }
        return points;
    }

    public static int pointsFromPurchaseDate(final LocalDate purchaseDate) {
        int day = purchaseDate.getDayOfMonth();
        if (day % 2 != 0)
            return 6;
        return 0;
    }
}
