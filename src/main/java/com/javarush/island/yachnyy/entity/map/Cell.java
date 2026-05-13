package com.javarush.island.yachnyy.entity.map;

import com.javarush.island.yachnyy.entity.organisms.Organism;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {

    private static int idCounter = 0;

    @Getter
    private final int id;
    @Getter
    private final int row;
    @Getter
    private final int col;

    private final Lock lock = new ReentrantLock(false);
    private final ResidentMap residents = new ResidentMap();

    private final List<Cell> nextCell = new ArrayList<>();

    public Cell(int row, int col) {
        this.id = idCounter++;
        this.row = row;
        this.col = col;
    }

    public Cell getNextCell(int countMovement) {

        Cell currentCell = this;

        for (int i = 0; i < countMovement; i++) {
            List<Cell> availableNeighbors = currentCell
                    .nextCell
                    .stream()
                    .toList();

            if (availableNeighbors.isEmpty()) {
                break;
            }

            int rndNumber = ThreadLocalRandom.current().nextInt(availableNeighbors.size());
            currentCell = availableNeighbors.get(rndNumber);
        }

        return currentCell;
    }

    public Lock getLock() {
        return lock;
    }

    public ResidentMap getResidents() {
        return residents;
    }

    public void addNeighbour(Cell cell) {
        nextCell.add(cell);
    }

    public List<Cell> getNeighbors() {
        return nextCell;
    }

    @Override
    public String toString() {
        return String.format("Cell[%d,%d]", row, col);
    }


    public double eatPlant(double needKg) {
        return residents.eatPlant(needKg);
    }

    public void growPlants(double amount, double max) {
        residents.growPlants(amount, max);
    }

    public void addOrganism(Organism o) {
        residents.add(o);
    }

    public void removeOrganism(Organism o) {
        residents.remove(o);
    }

    public void cleanDead() {
        residents.removeDeadAndEmpty();
    }
}
