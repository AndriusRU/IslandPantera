package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Овца",
        icon           = "🐑",
        maxWeight      = 70,
        maxCountInCell = 140,
        maxSpeed       = 3,
        maxFood        = 15
)
public class Sheep extends Herbivore {
}
