package com.javarush.island.yachnyy.services;

import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.map.Cell;
import com.javarush.island.yachnyy.entity.map.GameMap;
import com.javarush.island.yachnyy.entity.organisms.Animal;
import com.javarush.island.yachnyy.entity.organisms.Organism;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationTask implements Runnable {

    private final Cell cell;
    private final GameMap map;

    public SimulationTask(Cell cell, GameMap map) {
        this.cell = cell;
        this.map = map;
    }

    @Override
    public void run() {
        List<Organism> organismsBefore = new ArrayList<>(cell.getResidents().getAll());
        if (organismsBefore.isEmpty()) return;

        // Перемешиваем организмы
        Collections.shuffle(organismsBefore, new Random(ThreadLocalRandom.current().nextLong()));

        // 1. Тратим энергию
        for (Organism organism : organismsBefore) {
            if (organism.isAlive()) {
                organism.reduceEnergy(SimulationSettings.HUNGER_RATE);
            }
        }

        // 2. Размножение
        Set<Class<?>> reproducedAnimals = new HashSet<>();
        for (Organism organism : organismsBefore) {
            if (!organism.isAlive()) {
                continue;
            }

            if (reproducedAnimals.contains(organism.getClass())) {
                continue;
            }

            if (organism instanceof Animal animal) {
                animal.reproduce(cell);
                reproducedAnimals.add(animal.getClass());
            }
        }

        // 3. Еда
        for (Organism organism : organismsBefore) {
            if (organism.isAlive() && organism instanceof Animal animal) {
                animal.eat(cell);
            }
        }

        // 4. Перемещение
        for (Organism organism : organismsBefore) {
            if (organism.isAlive() && organism instanceof Animal animal && !animal.isMovedThisTurn()) {
                animal.move(cell, map);
            }
        }

        // 5. Удаление мертвых
        cell.cleanDead();

        // 6. Удаляем флаги перемещения
        for (Organism organism : cell.getResidents().getAll()) {
            if (organism instanceof Animal animal) {
                animal.resetTurnFlags();
            }
        }
    }
}
