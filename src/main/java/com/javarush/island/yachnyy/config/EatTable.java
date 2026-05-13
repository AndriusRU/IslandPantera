package com.javarush.island.yachnyy.config;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.HashMap;
import java.util.Map;

public class EatTable {

    public static final String PLANT_MARKER = "Plant";
    private EatTable() {};

    private static final Map<String, Map<String, Integer>> EAT_TABLE = new HashMap<>();

    static {
        put("Wolf", "Horse", 10);
        put("Wolf", "Deer", 15);
        put("Wolf", "Rabbit", 60);
        put("Wolf", "Mouse", 80);
        put("Wolf", "Goat", 60);
        put("Wolf", "Sheep", 70);
        put("Wolf", "Boar", 15);
        put("Wolf", "Buffalo", 10);
        put("Wolf", "Duck", 40);

        put("Boa", "Fox", 15);
        put("Boa", "Rabbit", 20);
        put("Boa", "Mouse", 40);
        put("Boa", "Duck", 10);

        put("Fox", "Rabbit", 70);
        put("Fox", "Mouse", 90);
        put("Fox", "Duck", 60);
        put("Fox", "Caterpillar", 40);

        put("Bear", "Boa", 80);
        put("Bear", "Horse", 40);
        put("Bear", "Deer", 80);
        put("Bear", "Rabbit", 80);
        put("Bear", "Mouse", 90);
        put("Bear", "Goat", 70);
        put("Bear", "Sheep", 70);
        put("Bear", "Boar", 50);
        put("Bear", "Buffalo", 20);
        put("Bear", "Duck", 10);

        put("Eagle", "Fox", 10);
        put("Eagle", "Rabbit", 90);
        put("Eagle", "Mouse", 90);
        put("Eagle", "Duck", 80);

        put("Eagle", "Fox", 10);
        put("Eagle", "Rabbit", 90);
        put("Eagle", "Mouse", 90);
        put("Eagle", "Duck", 80);

        put("Horse", PLANT_MARKER, 100);
        put("Deer", PLANT_MARKER, 100);
        put("Rabbit", PLANT_MARKER, 100);
        put("Goat", PLANT_MARKER, 100);
        put("Sheep", PLANT_MARKER, 100);
        put("Buffalo", PLANT_MARKER, 100);
        put("Caterpillar", PLANT_MARKER, 100);

        put("Mouse", PLANT_MARKER, 100);
        put("Mouse", "Caterpillar", 90);

        put("Boar", PLANT_MARKER, 100);
        put("Boar", "Mouse", 100);
        put("Boar", "Caterpillar", 100);

        put("Duck", PLANT_MARKER, 100);
        put("Duck", "Caterpillar", 90);
    }

    private static void put(String eater, String prey, int probability) {
        EAT_TABLE.computeIfAbsent(eater, k -> new HashMap<>()).put(prey, probability);
    }

    public static int getProbability(String eaterClass, String preyClass) {
        Map<String, Integer> preys = EAT_TABLE.get(eaterClass);
        if (preys == null) return 0;

        return preys.getOrDefault(preyClass, 0);
    }

    public static Map<String, Integer> getPreysFor(String eaterClass) {
        return EAT_TABLE.getOrDefault(eaterClass, Map.of());
    }
}
