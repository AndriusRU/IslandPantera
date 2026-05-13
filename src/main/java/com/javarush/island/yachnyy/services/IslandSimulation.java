package com.javarush.island.yachnyy.services;

import com.javarush.island.yachnyy.entity.map.GameMap;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IslandSimulation {

    private final GameMap map;
    private final AtomicInteger tact = new AtomicInteger(0);
}
