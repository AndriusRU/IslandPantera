package com.javarush.island.yachnyy.entity.organisms.herbivores;

import com.javarush.island.yachnyy.api.annotation.OrganismData;

@OrganismData(
        name           = "Буйвол",
        icon           = "🐃",
        maxWeight      = 700,
        maxCountInCell = 10,
        maxSpeed       = 3,
        maxFood        = 100
)
public class Buffalo extends Herbivore {
}
