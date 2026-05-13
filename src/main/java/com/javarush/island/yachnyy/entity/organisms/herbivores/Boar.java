package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Кабан",
        icon           = "🐗",
        maxWeight      = 400,
        maxCountInCell = 50,
        maxSpeed       = 2,
        maxFood        = 50
)
public class Boar extends Herbivore {
}
