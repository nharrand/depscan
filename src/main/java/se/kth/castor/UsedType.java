package se.kth.castor;

import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

import java.util.HashSet;
import java.util.Set;

public class UsedType {
    CtType type;
    Set<CtMethod> usedMethods;
    Set<CtConstructor> usedConstructors;

    public UsedType (CtType type) {
        this.type = type;
        usedConstructors = new HashSet<>();
        usedMethods = new HashSet<>();
    }
}
