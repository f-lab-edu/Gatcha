package org.example.gacha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.example.gacha.model.Item;
import org.example.gacha.model.Rarity;
import org.example.gacha.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GachaServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private GachaService gachaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock item lists
        List<Item> rItems = new ArrayList<>();
        rItems.add(Item.builder().rarity(Rarity.R).isPickup(false).build());
        rItems.add(Item.builder().rarity(Rarity.R).isPickup(false).build());
        rItems.add(Item.builder().rarity(Rarity.R).isPickup(true).build());

        List<Item> srItems = new ArrayList<>();
        srItems.add(Item.builder().rarity(Rarity.SR).isPickup(false).build());
        srItems.add(Item.builder().rarity(Rarity.SR).isPickup(false).build());
        srItems.add(Item.builder().rarity(Rarity.SR).isPickup(true).build());

        List<Item> ssrItems = new ArrayList<>();
        ssrItems.add(Item.builder().rarity(Rarity.SSR).isPickup(false).build());
        ssrItems.add(Item.builder().rarity(Rarity.SSR).isPickup(false).build());
        ssrItems.add(Item.builder().rarity(Rarity.SSR).isPickup(true).build());

        when(itemRepository.findByRarity(Rarity.R)).thenReturn(rItems);
        when(itemRepository.findByRarity(Rarity.SR)).thenReturn(srItems);
        when(itemRepository.findByRarity(Rarity.SSR)).thenReturn(ssrItems);
    }

    @Test
    void testDrawProbabilities() {
        int numTrials = 10000;
        int ssrCount = 0;
        int srCount = 0;
        int rCount = 0;

        for (int i = 0; i < numTrials; i++) {
            List<Item> items = gachaService.draw(10);
            for(Item item : items){
                if (Rarity.SSR.equals(item.getRarity())) {
                    ssrCount++;
                } else if (Rarity.SR.equals(item.getRarity())) {
                    srCount++;
                } else if (Rarity.R.equals(item.getRarity())) {
                    rCount++;
                }
            }
        }

        double ssrProbability = ssrCount / (double) (numTrials * 10);
        double srProbability = srCount / (double) (numTrials * 10);
        double rProbability = rCount / (double) (numTrials * 10);

        System.out.println("ssrProbability: " + ssrProbability);
        System.out.println("srProbability: " + srProbability);
        System.out.println("rProbability: " + rProbability);

        assertEquals(0.04, ssrProbability, 0.01);
        assertEquals(0.43, srProbability, 0.01);
        assertEquals(0.53, rProbability, 0.01);
    }
    @Test
    void testDrawProbabilities_PickUp() {
        int numTrials = 10000;
        int ssrCount = 0;
        int srCount = 0;
        int rCount = 0;

        int ssrPickupCount = 0;
        int srPickupCount = 0;
        int rPickupCount = 0;

        for (int i = 0; i < numTrials; i++) {
            List<Item> items = gachaService.draw(10);
            for(Item item : items){
                if (Rarity.SSR.equals(item.getRarity())) {
                    ssrCount++;
                    if(item.isPickup()) ssrPickupCount++;
                } else if (Rarity.SR.equals(item.getRarity())) {
                    srCount++;
                    if(item.isPickup()) srPickupCount++;
                } else if (Rarity.R.equals(item.getRarity())) {
                    rCount++;
                    if(item.isPickup()) rPickupCount++;
                }
            }
        }

        double ssrPickup = (double) ssrPickupCount / ssrCount;
        double srPickup = (double) srPickupCount / srCount;
        double rPickup = (double) rPickupCount / rCount;

        System.out.println("ssrProbability: " + ssrPickup);
        System.out.println("srProbability: " + srPickup);
        System.out.println("rProbability: " + rPickup);

        assertEquals(0.5, ssrPickup, 0.01);
        assertEquals(0.5, srPickup, 0.01);
        assertEquals(0.5, rPickup, 0.01);
    }
}