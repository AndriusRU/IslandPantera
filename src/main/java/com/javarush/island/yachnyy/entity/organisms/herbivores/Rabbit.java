package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Заяц",
        icon           = "🐰",
        maxWeight      = 2,
        maxCountInCell = 150,
        maxSpeed       = 2,
        maxFood        = 0.45
)
public class Rabbit extends Herbivore {
}
