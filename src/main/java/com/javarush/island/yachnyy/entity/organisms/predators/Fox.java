package com.javarush.island.yachnyy.entity.organisms.predators;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Лиса",
        icon           = "🦊",
        maxWeight      = 8,
        maxCountInCell = 30,
        maxSpeed       = 2,
        maxFood        = 2
)
public class Fox extends Predator {
}
