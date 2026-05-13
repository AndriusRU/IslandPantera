package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Лошадь",
        icon           = "🐎",
        maxWeight      = 400,
        maxCountInCell = 20,
        maxSpeed       = 4,
        maxFood        = 60
)
public class Horse extends Herbivore {
}
