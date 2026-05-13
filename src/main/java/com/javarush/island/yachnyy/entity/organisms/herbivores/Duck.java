package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Утка",
        icon           = "🦆",
        maxWeight      = 1,
        maxCountInCell = 200,
        maxSpeed       = 4,
        maxFood        = 0.15
)
public class Duck extends Herbivore {
}
