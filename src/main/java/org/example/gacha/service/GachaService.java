package org.example.gacha.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.gacha.model.Item;
import org.example.gacha.model.Rarity;
import org.example.gacha.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GachaService {

    private final ItemRepository itemRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final int PROBABILITY_MAX_RANGE = 100;
    private final int PROBABILITY_MIN_RANGE = 0;
    private final int SSR_PROBABILITY = 4;
    private final int SR_PROBABILITY = 47;
    private final int DRAW_COUNT = 10;


    public Item draw() {
        Rarity rarity = getRandomRarity();
        List<Item> items = itemRepository.findByRarity(rarity);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items found for rarity: " + rarity);
        }
        return getRandomItem(items);
    }

    public List<Item> drawTen() {
        return secureRandom.ints(DRAW_COUNT, PROBABILITY_MIN_RANGE, PROBABILITY_MAX_RANGE)
            .mapToObj(this::getRarityFromInt)
            .map(rarity -> {
                List<Item> items = itemRepository.findByRarity(rarity);
                if (items.isEmpty()) {
                    throw new IllegalArgumentException("No items found for rarity: " + rarity);
                }
                return getRandomItem(items);
            })
            .collect(Collectors.toList());
    }

    private Rarity getRandomRarity() {
        int roll = secureRandom.nextInt(PROBABILITY_MAX_RANGE);
        if (roll < SSR_PROBABILITY) return Rarity.SSR;
        if (roll < SR_PROBABILITY) return Rarity.SR;
        return Rarity.R;
    }

    private Rarity getRarityFromInt(int roll) {
        if (roll < SSR_PROBABILITY) return Rarity.SSR;
        if (roll < SR_PROBABILITY) return Rarity.SR;
        return Rarity.R;
    }
    private Item getRandomItem(List<Item> items) {
        return items.get(secureRandom.nextInt(items.size()));
    }
}