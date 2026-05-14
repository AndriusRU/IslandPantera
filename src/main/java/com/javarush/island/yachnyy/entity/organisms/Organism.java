package com.javarush.island.yachnyy.entity.organisms;

import com.javarush.island.yachnyy.api.annotation.OrganismData;
import com.javarush.island.yachnyy.api.entity.Reproducible;
import com.javarush.island.yachnyy.config.SimulationSettings;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Organism implements Reproducible, Cloneable {
    private final static AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    @Getter
    private final String name;
    @Getter
    private final String icon;
    @Getter
    private final int maxCountInCell;
    @Getter
    private final double maxWeight;
    @Getter
    private final int maxSpeed;
    @Getter
    private final double maxFood;
//    private final int countInGroup;

    @Getter
    private final double weight;
    @Getter
    private double currentFood;

    private AtomicBoolean alive = new AtomicBoolean(true);


//    public Organism(String name, String icon, Limit limit) {
//        this.name = name;
//        this.icon = icon;
//        this.limit = limit;
//        this.weight = ThreadLocalRandom.current().nextDouble(0.2 * limit.maxWeight(), limit.maxWeight());
//    }

    protected Organism() {
        OrganismData annotationData = this.getClass().getAnnotation(OrganismData.class);

        if (annotationData == null) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " должен иметь аннотацию @OrganismData");
        }

        this.name           = annotationData.name();
        this.icon           = annotationData.icon();
        this.maxCountInCell = annotationData.maxCountInCell();
        this.maxWeight      = annotationData.maxWeight();
        this.maxSpeed       = annotationData.maxSpeed();
        this.maxFood        = annotationData.maxFood();

        this.weight = ThreadLocalRandom.current().nextDouble(0.2 * maxWeight, maxWeight);

        this.currentFood = maxFood > 0 ? ThreadLocalRandom.current().nextDouble(0, maxFood) : 0;

    }

    public boolean isAlive() {
        return alive.get();
    }

    public void die() {
        alive.set(false);
    }

    public void addFood(double amount) {
        currentFood = Math.min(maxFood, currentFood + amount);
    }

    public void reduceEnergy(double rate) {
        if (maxFood <= 0) {
            return;
        }

        currentFood = Math.max(0, currentFood - maxFood * rate);
        if (currentFood <= 0) {
            die();
        }
    }

    // Насытился ли организм
    public boolean isSatiated() {
        return currentFood >= maxFood;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Organism clone = (Organism) super.clone();
        clone.alive = new AtomicBoolean(true);
        return clone;
    }

    public Organism createChild() {
            try {
                Organism child = (Organism) clone();
                child.currentFood = 4 * SimulationSettings.HUNGER_RATE * child.maxFood;
                return child;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
    }

    public Organism createAdult() {
        Organism organism = createChild();
        organism.currentFood = organism.maxFood;
        return organism;
    }

    @Override
    public String toString() {
        return "Organism{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
//        return icon + name;
    }
}
