package com.javarush.island.yachnyy.entity.map;

import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.organisms.Organism;
import com.javarush.island.yachnyy.entity.organisms.OrganismRegistry;
import com.javarush.island.yachnyy.entity.organisms.plants.Plant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameMap {


    private final Cell[][] cells;

    public GameMap(int rows, int cols) {

        cells = new Cell[rows][cols];

        createCells();
        initNeighbors();
    }

    /**
     * Создание клеток
     */
    private void createCells() {

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Связываем клетки соседями
     */
    private void initNeighbors() {

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell current = cells[row][col];
                // вверх
                if (row > 0) {
                    current.addNeighbour(cells[row - 1][col]);
                }

                // вниз
                if (row < cells.length - 1) {
                    current.addNeighbour(cells[row + 1][col]);
                }

                // влево
                if (col > 0) {
                    current.addNeighbour(cells[row][col - 1]);
                }

                // вправо
                if (col < cells[0].length - 1) {
                    current.addNeighbour(cells[row][col + 1]);
                }
            }
        }
    }

    // Заселяем остров
    public void initializationMap() {
        int rows = getRows();
        int cols = getCols();

        for (Class<? extends Organism> aClass : OrganismRegistry.getAllClasses()) {
            Organism prototype = OrganismRegistry.getPrototype(aClass.getSimpleName());

            if (prototype == null) {
                continue;
            }

            if (prototype instanceof Plant) {
                continue;
            }

            int maxCountInCell = prototype.getMaxCountInCell();
            int totalCells = rows * cols;

            int startCountPrototype = Math.max(1, (int)(totalCells * maxCountInCell * SimulationSettings.INIT_PERCENT_ANIMAL));
//            System.out.println("Start count proto " + startCountPrototype);

            for (int i = 0; i < startCountPrototype; i++) {
                int row = ThreadLocalRandom.current().nextInt(rows);
                int col = ThreadLocalRandom.current().nextInt(cols);

                Cell cell = cells[row][col];
                Organism organism = OrganismRegistry.initialBorn(aClass);

                if (cell.getResidents().canAdd(organism)) {
                    cell.addOrganism(organism);
                }
            }
        }

//        double maxPlants = 200.0;
//        for (Cell cell : allCells()) {
//            cell.growPlants(maxPlants * 0.1, maxPlants);
//        }

        OrganismRegistry.getPlantPrototype().ifPresent(proto -> {
            double maxMass = proto.maxMassPlantPerCell();
            for (Cell cell : allCells()) {
                cell.growPlants(maxMass * 0.2, maxMass);
            }
        });

    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

    public List<Cell> allCells() {
        List<Cell> list = new ArrayList<>(getRows() * getCols());
        for (Cell[] row : cells) {
            list.addAll(Arrays.asList(row));
        }

        return list;
    }
}