package org.example.gacha.controller;

import lombok.RequiredArgsConstructor;
import org.example.gacha.dto.ItemDto;
import org.example.gacha.service.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item/add")
    public boolean addItem(@RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto);
    }
}
