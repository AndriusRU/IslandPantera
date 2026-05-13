package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Гусеница",
        icon           = "🐛",
        maxWeight      = 0.01,
        maxCountInCell = 1000,
        maxSpeed       = 0,
        maxFood        = 0
)
public class Caterpillar extends Herbivore {
}
