package com.javarush.island.yachnyy.config;

public class SimulationSettings {

    private SimulationSettings() {}

    // == Параметры острова ==
    public static final int ISLAND_WIDTH = 20;
    public static final int ISLAND_HEIGHT = 10;

    // == Параметры симуляции ==
    public static final int DURATION_TACT = 500;      // длительность такта (500 мс)
    public static final int MAX_COUNT_TACTS = 20;    // макс кол-во тактов
    public static final int PLANT_GROW_TACT = 10;       // Размножение растений
    public static final double INIT_PERCENT_ANIMAL = 0.01;   // Начальный процент животных

    public static final int MIN_FOR_REPRODUCTION = 2;   // Минимальное кол-во особей для размножения
    public static final int MAX_CHILDREN_FROM_PAIR = 4; // Максимум детенышей от одной пары
    public static final int MIN_CHILDREN_FROM_PAIR = 1; // Минимум детенышей от одной пары

    public static final double HUNGER_RATE = 0.1;       // Доля от максимального количества еды для насыщения, которое тратит животное на 1 такт

    public static final boolean STOP_ALL_DEAD = true;   // Остановить симуляцию, если все животные погибли

    public static final int WORKER_THREAD_COUNT = 0;
    public static final long SHUTDOWN_TIMEOUT_SECONDS = 10;

    public static final int MAP_PRINT_INTERVAL = 3;
    public static final int MAP_DISPLAY_COLS = 25;
    public static final int MAP_DISPLAY_ROWS = 10;
    public static final int EXTENDED_STATS_INTERVAL = 20;
}
