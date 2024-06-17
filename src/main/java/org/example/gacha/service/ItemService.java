package org.example.gacha.service;

import lombok.RequiredArgsConstructor;
import org.example.gacha.dto.ItemDto;
import org.example.gacha.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public boolean addItem(ItemDto itemDto) {
        if(itemRepository.existsByName(itemDto.getName())) {
            throw new RuntimeException(new IllegalAccessException());
        }
        itemRepository.save(itemDto.toEntity());
        return true;
    }
}
