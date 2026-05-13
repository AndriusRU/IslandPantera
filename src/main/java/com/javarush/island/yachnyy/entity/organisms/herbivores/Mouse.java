package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Мышь",
        icon           = "🐁",
        maxWeight      = 0.05,
        maxCountInCell = 500,
        maxSpeed       = 1,
        maxFood        = 0.01
)
public class Mouse extends Herbivore {
}
