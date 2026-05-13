package com.javarush.island.yachnyy.entity.map;

import com.javarush.island.yachnyy.entity.organisms.Organism;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResidentMap {
    private final Map<Class<? extends Organism>, List<Organism>> residents = new ConcurrentHashMap<>();

    private volatile double plantMass = 0;

    // Plants
    public double getPlantMass() {
        return plantMass;
    }

    public synchronized void growPlants(double amount, double max) {
        plantMass = Math.min(max, plantMass + amount);
    }

    public synchronized double eatPlant(double needKg) {
        double eaten = Math.min(plantMass, needKg);
        plantMass = Math.max(0, plantMass - eaten);
        return eaten;
    }


    public void add(Organism organism) {
        residents
                .computeIfAbsent(organism.getClass(),aClass -> new CopyOnWriteArrayList<>())
                .add(organism);
    }

    public void remove(Organism organism) {
        List<Organism> organisms = residents.get(organism.getClass());

        if (organisms != null) {
            organisms.remove(organism);

//            if (organisms.isEmpty()) {
//                residents.remove(organism.getClass());
//            }
        }
    }

    // Получить определенный организм
    public List<Organism> get(Class<? extends Organism> type) {
        List<Organism> organismList = residents.get(type);
//        return residents.getOrDefault(type, Collections.emptyList());
        return organismList == null ? Collections.emptyList() : Collections.unmodifiableList(organismList);
    }

    public List<Organism> get(String simpleClassName) {
        for (Map.Entry<Class<? extends Organism>, List<Organism>> classListEntry : residents.entrySet()) {
            if (classListEntry.getKey().getSimpleName().equals(simpleClassName)) {
                return Collections.unmodifiableList(classListEntry.getValue());
            }
        }

        return Collections.emptyList();
    }

    // Получаем жертву для поедания. 1 жертву может есть 1 хищник
    public synchronized Organism getOne(String simpleClassName) {
        for (Map.Entry<Class<? extends Organism>, List<Organism>> classListEntry : residents.entrySet()) {
            if (!classListEntry.getKey().getSimpleName().equals(simpleClassName)) {
                continue;
            }

            List<Organism> list = classListEntry.getValue();
            for (int i = 0; i < list.size(); i++) {
                Organism organism = list.get(i);
                if (organism.isAlive()) {
                    list.remove(i);
                    return  organism;
                }
            }
        }

        return null;
    }

    // Получить всех организмов клетки
    public List<Organism> getAll() {
        List<Organism> aliveOrganisms = new ArrayList<>();
        for (List<Organism> organismList : residents.values()) {
            for (Organism organism : organismList) {
                if (organism.isAlive()) {
                    aliveOrganisms.add(organism);
                }
            }
        }

        return aliveOrganisms;
//        return residents.values()
//                .stream()
//                .flatMap(List::stream)
//                .toList();
    }

    public boolean canAdd(Organism organism) {
        List<Organism> list = residents.get(organism.getClass());
        int current = (list == null) ? 0 : list.size();
        return current < organism.getMaxCountInCell();
    }

    public void removeDeadAndEmpty() {
        for (List<Organism> list : residents.values()) {
            list.removeIf(o -> !o.isAlive());
        }
        residents.entrySet().removeIf(e -> e.getValue().isEmpty());
    }

    // Количество организмов типа
    public int count(Class<? extends Organism> type) {
        List<Organism> organismList = residents.get(type);
        return organismList == null ? 0: organismList.size();
    }

    // Общее количество жителей
    public int totalSize() {
        return residents.values()
                .stream()
                .mapToInt(List::size)
                .sum();
    }

    // Есть ли жители
    public boolean isEmpty() {
        return residents.values().stream().allMatch(List::isEmpty);
    }

}
