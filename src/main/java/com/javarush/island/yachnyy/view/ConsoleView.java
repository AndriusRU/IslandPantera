package com.javarush.island.yachnyy.view;

import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.map.Cell;
import com.javarush.island.yachnyy.entity.map.GameMap;
import com.javarush.island.yachnyy.entity.organisms.Organism;

import java.util.*;

public class ConsoleView {

    public static void printStats(GameMap map, int tact) {

        Map<String, Integer> counts = new TreeMap<>();

        double totalPlants = 0;
        int totalAnimals = 0;

        for (Cell cell : map.allCells()) {
            totalPlants += cell.getResidents().getPlantMass();

            for (Organism organism : cell.getResidents().getAll()) {
                counts.merge(organism.getIcon() + " " + organism.getName(),1, Integer::sum);
                totalAnimals++;
            }
        }

        System.out.println();

        System.out.println("═".repeat(70));
        System.out.println("🏝 ТАКТ " + tact);
        System.out.println("🐾 Животных: " + totalAnimals);
        System.out.println("🌿 Растений: " + (int) totalPlants + " кг");
        System.out.println("═".repeat(70));

        counts.forEach((name, count) ->
                System.out.printf(
                        "%-20s : %5d %s%n",
                        name,
                        count,
                        bar(count, 200, 20)
                )
        );
    }

    public static void printMap(GameMap map) {

        int displayCols = Math.min(SimulationSettings.MAP_DISPLAY_COLS, map.getCols());
        int displayRows = Math.min(SimulationSettings.MAP_DISPLAY_ROWS, map.getCols());

        System.out.println("┌── Карта " + "─".repeat(displayCols * 2) + "┐");
        for (int rowNumber = 0; rowNumber < displayRows; rowNumber++) {
            System.out.print("│");
            for (int colNumber = 0; colNumber < displayCols; colNumber++) {
                Cell cell = map.getCell(rowNumber, colNumber);
                List<Organism> all = cell.getResidents().getAll();
                Set<String> icons = new LinkedHashSet<>();

                for (Organism organism : all) {
                    icons.add(organism.getIcon());
                }

                if (cell.getResidents().getPlantMass() > 30) {
                    icons.add("🌿");
                }
                String s = icons.isEmpty() ? "▫ " : icons.iterator().next();
                System.out.printf("%-2s", s);
            }
            System.out.println("│");
        }
        System.out.println("└" + "─".repeat(displayCols * 2 + 2) + "┘");
    }

    private static String bar(int value, int max, int len) {
        int filled =
                Math.min(
                        len,
                        (int) (((double) value / max) * len)
                );

        return "█".repeat(filled) + "░".repeat(len - filled);
    }
}
