package org.fetch.rewards.entity;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public final class Receipt {
    @NotBlank
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private final String retailer;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate purchaseDate;
    @NotBlank
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "Time must be in HH:MM format")
    private final String purchaseTime;
    @NotEmpty
    private final List<Item> items;
    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private final String total;

}
