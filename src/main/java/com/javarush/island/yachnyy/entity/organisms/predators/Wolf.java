package com.javarush.island.yachnyy.entity.organisms.predators;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Волк",
        icon           = "🐺",
        maxWeight      = 50,
        maxCountInCell = 30,
        maxSpeed       = 3,
        maxFood        = 8
)
public class Wolf extends Predator {
}
