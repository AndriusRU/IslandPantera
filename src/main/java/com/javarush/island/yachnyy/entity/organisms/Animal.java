package com.javarush.island.yachnyy.entity.organisms;

import com.javarush.island.yachnyy.api.entity.Eater;
import com.javarush.island.yachnyy.api.entity.Movable;
import com.javarush.island.yachnyy.api.entity.Reproducible;
import com.javarush.island.yachnyy.config.EatTable;
import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.map.Cell;
import com.javarush.island.yachnyy.entity.map.GameMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Animal extends Organism implements Eater, Movable, Reproducible {

    private final AtomicBoolean movedThisTurn = new AtomicBoolean(false);

    public boolean isMovedThisTurn() {
        return movedThisTurn.get();
    }

    public void resetTurnFlags() {
        movedThisTurn.set(false);
    }

    @Override
    public int reproduce(Cell cell) {
        List<Organism> aliveOrganisms = new ArrayList<>();

        for (Organism organism : cell.getResidents().get(getClass())) {
            if (organism.isAlive()) {
                aliveOrganisms.add(organism);
            }
        }

        int countSameOrganisms = aliveOrganisms.size();
        if (countSameOrganisms < SimulationSettings.MIN_FOR_REPRODUCTION) {
            return 0;
        }

        int pairs = countSameOrganisms / 2;
        int born = 0;

        if (pairs < 1) {
            return 0;
        }

        for (int i = 0; i < pairs; i++) {
            int countChildren = ThreadLocalRandom.current().nextInt(
                    SimulationSettings.MIN_CHILDREN_FROM_PAIR,
                    SimulationSettings.MAX_CHILDREN_FROM_PAIR + 1
            );

            for (int j = 0; j < countChildren; j++) {
                if (!cell.getResidents().canAdd(this)) {
                    break;
                }
                cell.getResidents().add(createChild());
                born++;
            }
        }

        return born;
    }

    @Override
    public boolean move(Cell currentCell, GameMap map) {
        if (movedThisTurn.compareAndSet(false, true)) {
            return false;
        }

        if (!isAlive() || getMaxSpeed() == 0) {
            return false;
        }

        Cell target = currentCell.getNextCell(ThreadLocalRandom.current().nextInt(1, getMaxSpeed() + 1));
        if (target == currentCell) {
            return false;
        }

        Cell firstCell = currentCell.getId() < target.getId() ? currentCell : target;
        Cell secondCell = currentCell.getId() < target.getId() ? target : currentCell;

        firstCell.getLock().lock();
        try {
            secondCell.getLock().lock();
            try {
                if (!isAlive()) {
                    return false;
                }

                if (!target.getResidents().canAdd(this)) {
                    return false;
                }

                currentCell.getResidents().remove(this);
                target.getResidents().add(this);

//                movedThisTurn.set(true);
                return true;
            } finally {
                secondCell.getLock().unlock();
            }
        } finally {
            firstCell.getLock().unlock();
        }
    }

    @Override
    public boolean eat(Cell cell) {
        if (!isAlive() || isSatiated()) {
            return false;
        }

        String myClass = getClass().getSimpleName();
        Map<String, Integer> preysFor = EatTable.getPreysFor(myClass);
        if (preysFor.isEmpty()) return false;

        boolean ate = false;
        List<String> preyTypes = new ArrayList<>(preysFor.keySet());
        Collections.shuffle(preyTypes);

        for (String preyType : preyTypes) {
            if (isSatiated()) {
                break;
            }

            int probability = preysFor.get(preyType);

            if (ThreadLocalRandom.current().nextInt(100) >= probability) {
                continue;
            }

            boolean willEaten = probability >= 100 || (probability > 0 && ThreadLocalRandom.current().nextInt(100) < probability);
            if (!willEaten) continue;

            if (EatTable.PLANT_MARKER.equals(preyType)) {
                double needFood = getMaxFood() - getCurrentFood();
                double eaten = cell.eatPlant(needFood);

                if (eaten > 0) {
                    addFood(eaten);
                    ate = true;
                }
            } else {
                Organism prey = cell.getResidents().getOne(preyType);
                if (prey != null) {
                    //prey.die();
                    addFood(prey.getWeight());
                    ate = true;
                }
            }
        }

        return ate;
    }
}
