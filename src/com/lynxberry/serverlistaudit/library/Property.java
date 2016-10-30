package com.lynxberry.serverlistaudit.library;

/**
 * Created by stevenshao on 25/10/2016.
 */
public class Property<Type> {
    private String propertyName;
    private Type propertyValue;
    public Property(String propertyName, Type propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    @Override
    public String toString() {
        return this.propertyName + ":" + this.propertyValue;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public Type getPropertyValue() {return this.propertyValue;}
}

