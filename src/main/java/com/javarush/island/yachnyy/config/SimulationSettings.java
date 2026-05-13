package com.javarush.island.yachnyy.config;

public class SimulationSettings {

    private SimulationSettings() {}

    // == Параметры острова ==
    public static final int ISLAND_WIDTH = 10;
    public static final int ISLAND_HEIGHT = 5;

    // == Параметры симуляции ==
    public static final int DURATION_TACT = 500;      // длительность такта (500 мс)
    public static final int MAX_COUNT_TACTS = 100;    // макс кол-во тактов
    public static final int PLANT_GROW_TACT = 10;       // Размножение растений

    public static final int MIN_FOR_REPRODUCTION = 2;   // Минимальное кол-во особей для размножения
    public static final int MAX_CHILDREN_FROM_PAIR = 4; // Максимум детенышей от одной пары
    public static final int MIN_CHILDREN_FROM_PAIR = 1; // Минимум детенышей от одной пары

    public static final double HUNGER_RATE = 0.3;       // Доля от максимального количества еды для насыщения, которое тратит животное на 1 такт

    public static final boolean STOP_ALL_DEAD = true;   // Остановить симуляцию, если все животные погибли
}
