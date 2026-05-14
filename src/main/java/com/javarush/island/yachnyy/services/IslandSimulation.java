package com.javarush.island.yachnyy.services;

import com.javarush.island.yachnyy.config.SimulationSettings;
import com.javarush.island.yachnyy.entity.map.Cell;
import com.javarush.island.yachnyy.entity.map.GameMap;
import com.javarush.island.yachnyy.entity.organisms.OrganismRegistry;
import com.javarush.island.yachnyy.entity.organisms.plants.Plant;
import com.javarush.island.yachnyy.view.ConsoleView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class IslandSimulation {

    private final GameMap map;
    private final double maxPlantMass;
    private final AtomicInteger tact = new AtomicInteger(0);
    private final AtomicBoolean running = new AtomicBoolean(false);

    private final ScheduledExecutorService sheduler =
            Executors.newSingleThreadScheduledExecutor(
                    r -> {
                        Thread thread = new Thread(r);
                        thread.setName("sheduler_simulation");
                        return thread;
                    }
            );

    private final ExecutorService workerPool = Executors.newFixedThreadPool(
            getCountWorker(),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("worker_cell-" + thread.threadId());
                return thread;
            }
    );

    public static int getCountWorker() {
        if (SimulationSettings.WORKER_THREAD_COUNT > 0) {
            return SimulationSettings.WORKER_THREAD_COUNT;
        }

        return Runtime.getRuntime().availableProcessors();
    }

    public IslandSimulation(GameMap map) {
        this.map = map;
        this.maxPlantMass = OrganismRegistry.getPlantPrototype().map(Plant::maxMassPlantPerCell).orElse(0.0);
    }

    public void start() {
        if (!running.compareAndSet(false, true)) {
            return;
        }

        System.out.println("Симуляция запущена.");
        ConsoleView.printStats(map, 0);

//        sheduler.scheduleWithFixedDelay(
//                () -> this.runTact(),
//                SimulationSettings.DURATION_TACT,
//                SimulationSettings.DURATION_TACT,
//                TimeUnit.MILLISECONDS
//        );
        sheduler.scheduleAtFixedRate(
                () -> this.runTact(),
                SimulationSettings.DURATION_TACT,
                SimulationSettings.DURATION_TACT,
                TimeUnit.MILLISECONDS
        );


    }

    private void runTact() {
        int currentTact = tact.incrementAndGet();

        try {
            growPlants();
            runAnimalLifyCicle();

            ConsoleView.printStats(map, currentTact);

            if (currentTact % SimulationSettings.MAP_PRINT_INTERVAL == 0) {
                ConsoleView.printMap(map);
            }

            if (shouldStop(currentTact)) {
                shutdownSimulation();
            }
        } catch (Exception e) {
            System.err.println("Ошибка на такте " + currentTact);
            e.printStackTrace();
        }
    }

    private void growPlants() {
        List<Callable<Void>> tasks = new ArrayList<>();
        int plantGrowTact = SimulationSettings.PLANT_GROW_TACT;

        for (Cell cell : map.allCells()) {
            tasks.add(() -> {
               cell.growPlants(plantGrowTact, maxPlantMass);
               return null;
            });
        }

        try {
            workerPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void runAnimalLifyCicle() {
        List<Callable<Void>> tasks = new ArrayList<>();

        for (Cell cell : map.allCells()) {
            tasks.add(() -> {
                new SimulationTask(cell, map).run();
                return null;
            });
        }
        try {
            workerPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean shouldStop(int currentTact) {
        int maxCountTacts = SimulationSettings.MAX_COUNT_TACTS;

        if (currentTact >= maxCountTacts) {
            System.out.println("Достигнут предел тактов для симуляции (" + maxCountTacts + ")");
            return true;
        }

        if (SimulationSettings.STOP_ALL_DEAD) {
            long total = map.allCells().stream()
                    .mapToLong(cell -> cell.getResidents().totalSize())
                    .sum();
            if (total == 0) {
                System.out.println("Все животные погибли.");
                return true;
            }
        }

        return false;
    }

    public void shutdownSimulation() {
        sheduler.shutdown();
        workerPool.shutdown();

        try {
            if (!workerPool.awaitTermination(5, TimeUnit.SECONDS)) {
                workerPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            workerPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Симуляция завершена. Такт - " + tact.get());
    }
}
