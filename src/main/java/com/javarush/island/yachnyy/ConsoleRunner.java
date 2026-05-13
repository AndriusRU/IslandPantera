package com.javarush.island.yachnyy;

import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.map.GameMap;

public class ConsoleRunner {

    public static void main(String[] args) {
        registerAllOrganisms();

        GameMap map = new GameMap(SimulationSettings.ISLAND_HEIGHT, SimulationSettings.ISLAND_WIDTH);
        map.initializationMap();
    }

    private static void registerAllOrganisms() {
        registerAllOrganisms();
    }
}
