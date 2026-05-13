package com.javarush.island.yachnyy.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OrganismData {
    String name();
    String icon();
    double maxWeight();
    int maxCountInCell();
    int maxSpeed();
    double maxFood();
    int countInGroup() default 1;
}
