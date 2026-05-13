package com.javarush.island.yachnyy.entity.organisms.plants;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Трава",
        icon           = "🌿",
        maxWeight      = 1,
        maxCountInCell = 200,
        maxSpeed       = 0,
        maxFood        = 0
)
public class Grass extends Plant {
}