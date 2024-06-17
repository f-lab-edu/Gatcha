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
        rItems.add(Item.builder().id(1L).name("item1").rarity(Rarity.R).build());
        rItems.add(Item.builder().id(2L).name("item2").rarity(Rarity.R).build());

        List<Item> srItems = new ArrayList<>();
        srItems.add(Item.builder().id(3L).name("item3").rarity(Rarity.SR).build());
        srItems.add(Item.builder().id(4L).name("item4").rarity(Rarity.SR).build());

        List<Item> ssrItems = new ArrayList<>();
        ssrItems.add(Item.builder().id(5L).name("item5").rarity(Rarity.SSR).build());
        ssrItems.add(Item.builder().id(6L).name("item6").rarity(Rarity.SSR).build());

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
}