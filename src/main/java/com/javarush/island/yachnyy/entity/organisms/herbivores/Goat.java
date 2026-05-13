package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Коза",
        icon           = "🐐",
        maxWeight      = 60,
        maxCountInCell = 140,
        maxSpeed       = 3,
        maxFood        = 10
)
public class Goat extends Herbivore {
}
