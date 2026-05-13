package com.javarush.island.yachnyy.api.entity;

import com.javarush.island.yachnyy.entity.map.Cell;
import com.javarush.island.yachnyy.entity.map.GameMap;

public interface Movable {
    boolean move(Cell cell, GameMap map);
}
