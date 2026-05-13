package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Олень",
        icon           = "🦌",
        maxWeight      = 300,
        maxCountInCell = 20,
        maxSpeed       = 4,
        maxFood        = 50
)
public class Deer extends Herbivore {
}
