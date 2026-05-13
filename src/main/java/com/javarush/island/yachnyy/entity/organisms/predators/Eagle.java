package com.javarush.island.yachnyy.entity.organisms.predators;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Орел",
        icon           = "🦅",
        maxWeight      = 6,
        maxCountInCell = 20,
        maxSpeed       = 3,
        maxFood        = 1
)
public class Eagle extends Predator {
}
