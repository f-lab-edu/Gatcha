package org.example.gacha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.gacha.model.Item;
import org.example.gacha.model.Rarity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String name;
    private Rarity rarity;
    private boolean pickup;

    public Item toEntity() {
        return Item.builder()
            .name(this.name)
            .rarity(this.rarity)
            .isPickup(this.pickup)
            .build();
    }
}
