package com.javarush.island.yachnyy.entity.organisms.predators;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Медведь",
        icon           = "🐻",
        maxWeight      = 500,
        maxCountInCell = 5,
        maxSpeed       = 2,
        maxFood        = 80
)
public class Bear extends Predator {
}
