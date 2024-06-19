package org.example.gacha.repository;

import java.util.List;
import org.example.gacha.model.Item;
import org.example.gacha.model.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByRarity(Rarity rarity);
    boolean existsByName(String name);
}
