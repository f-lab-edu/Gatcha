package org.example.gacha.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.gacha.model.Item;
import org.example.gacha.service.GachaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GachaController {

    private final GachaService gachaService;

    @GetMapping("/draw")
    public Item draw() {
        return gachaService.draw();
    }

    @GetMapping("/draw-ten")
    public List<Item> drawTen() {
        return gachaService.drawTen();
    }
}
