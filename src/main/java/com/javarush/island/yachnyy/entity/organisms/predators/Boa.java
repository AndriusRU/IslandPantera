package com.javarush.island.yachnyy.entity.organisms.predators;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Удав",
        icon           = "🐍",
        maxWeight      = 15,
        maxCountInCell = 30,
        maxSpeed       = 1,
        maxFood        = 3
)
public class Boa extends Predator {
}
