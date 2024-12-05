package org.fetch.rewards.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public final class Item {
    @NotBlank
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    public final String shortDescription;
    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    public final String price;
}
