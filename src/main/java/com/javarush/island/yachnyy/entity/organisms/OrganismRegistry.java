package com.javarush.island.yachnyy.entity.organisms;

import com.javarush.island.yachnyy.api.annotation.OrganismData;
import com.javarush.island.yachnyy.entity.organisms.plants.Plant;

import javax.print.attribute.standard.Finishings;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrganismRegistry {

    private static final Map<String, Organism> prototypes = new ConcurrentHashMap<>();

    private static final List<Class<? extends Organism>> registeredClasses = new ArrayList<>();

    public static void register(Class<? extends Organism> aClass) {
        if (!aClass.isAnnotationPresent(OrganismData.class)) {
            throw new IllegalArgumentException(
                    aClass.getSimpleName() + " не имеет аннотации @OrganismData"
            );
        }

        try {
            Organism prototype = aClass.getDeclaredConstructor().newInstance();
            prototypes.put(aClass.getSimpleName(), prototype);
            registeredClasses.add(aClass);
        } catch (Exception e) {
            throw new RuntimeException("Не могу создать прототип " + aClass.getSimpleName(), e);
        }
    }

    public static Organism initialBorn(Class<? extends Organism> aClass) {
        Organism prototype = prototypes.get(aClass.getSimpleName());
        if (prototype == null) {
            throw new IllegalStateException(aClass.getSimpleName() + " не зарегистрирован");
        }
        return prototype.createAdult();
    }

    public static Organism initialBorn(String className) {
        Organism prototype = prototypes.get(className);
        if (prototype == null) {
            throw new IllegalStateException(className + " не зарегистрирован");
        }
        return prototype.createAdult();
    }

    public static List<Class<? extends Organism>> getAllClasses() {
        return Collections.unmodifiableList(registeredClasses);
    }

    public static Collection<Organism> getAllPrototypes() {
        return Collections.unmodifiableCollection(prototypes.values());
    }

    public static Organism getPrototype(String className) {
        return prototypes.get(className);
    }

    public static Optional<Plant> getPlantPrototype() {
        return prototypes.values().stream()
                .filter(o -> o instanceof Plant)
                .map(o -> (Plant) o)
                .findFirst();
    }
}
