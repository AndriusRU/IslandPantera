package com.javarush.island.yachnyy.entity.organisms.plants;

import com.javarush.island.yachnyy.api.annotation.OrganismData;
import com.javarush.island.yachnyy.entity.map.Cell;
import com.javarush.island.yachnyy.entity.organisms.Organism;

public class Plant extends Organism {

    @Override
    public int reproduce(Cell cell) {
        return 0;
    }

    public double maxMassPlantPerCell() {
        OrganismData annotation = this.getClass().getAnnotation(OrganismData.class);
        return (double) annotation.maxCountInCell() * annotation.maxWeight();
    }
}
