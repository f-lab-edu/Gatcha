package org.example.gacha.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.gacha.model.Item;
import org.example.gacha.service.GachaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GachaController {

    private final GachaService gachaService;

    @PostMapping("/draw")
    public List<Item> draw(@RequestParam int count) {
        return gachaService.draw(count);
    }
}
