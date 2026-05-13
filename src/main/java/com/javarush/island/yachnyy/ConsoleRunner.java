package com.javarush.island.yachnyy;

import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.map.GameMap;
import com.javarush.island.yachnyy.entity.organisms.OrganismRegistry;
import com.javarush.island.yachnyy.entity.organisms.herbivores.*;
import com.javarush.island.yachnyy.entity.organisms.plants.Grass;
import com.javarush.island.yachnyy.entity.organisms.predators.*;

public class ConsoleRunner {

    public static void main(String[] args) {
        registerAllOrganisms();

        GameMap map = new GameMap(SimulationSettings.ISLAND_HEIGHT, SimulationSettings.ISLAND_WIDTH);
        map.initializationMap();
    }

    private static void registerAllOrganisms() {
        OrganismRegistry.register(Grass.class);

        OrganismRegistry.register(Bear.class);
        OrganismRegistry.register(Boa.class);
        OrganismRegistry.register(Eagle.class);
        OrganismRegistry.register(Fox.class);
        OrganismRegistry.register(Wolf.class);

        OrganismRegistry.register(Boar.class);
        OrganismRegistry.register(Buffalo.class);
        OrganismRegistry.register(Caterpillar.class);
        OrganismRegistry.register(Deer.class);
        OrganismRegistry.register(Duck.class);
        OrganismRegistry.register(Goat.class);
        OrganismRegistry.register(Horse.class);
        OrganismRegistry.register(Mouse.class);
        OrganismRegistry.register(Rabbit.class);
        OrganismRegistry.register(Sheep.class);

        System.out.println("Зарегистрировано видов: " + OrganismRegistry.getAllClasses().size());
    }
}
