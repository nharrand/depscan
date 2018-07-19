package se.kth.castor;

import org.apache.maven.model.Dependency;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.SpoonClassNotFoundException;

import java.util.*;

public class Library {
    String name;
    Set<String> packagesName;
    Set<CtPackage> packages;
    Dependency dependency;
    Map<CtType, UsedType> usedTypes;
    Map<Class, Set<CtTypedElement>> elem;


    public Library(String name) {
        this.name = name;
        usedTypes = new HashMap<>();
        elem = new HashMap<>();
    }

    public void init(CtModel model) {
        List<CtElement> named = model.getElements(new TypeFilter(CtTypedElement.class));
        for(CtElement el : named) {
            CtTypedElement namedElement = (CtTypedElement) el;
            try {
                if (namedElement.getType() != null && namedElement.getType().getQualifiedName().startsWith(name)) {
                    if(!elem.containsKey(namedElement.getClass())) {
                        elem.put(namedElement.getClass(), new HashSet<>());
                    }
                    elem.get(namedElement.getClass()).add(namedElement);
                }
            } catch (SpoonClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        List<CtElement> calls = model.getElements(new TypeFilter(CtInvocation.class));
        for(CtElement el : calls) {
            CtInvocation invocation = (CtInvocation) el;
            try {
                if (invocation.getType().getQualifiedName().startsWith(name)) {
                    CtExecutable executable = invocation.getExecutable().getExecutableDeclaration();
                    CtType type = executable.getType().getTypeDeclaration();

                    if (!usedTypes.containsKey(type)) {
                        usedTypes.put(type, new UsedType(type));
                    }
                    UsedType ut = usedTypes.get(type);

                    if (executable instanceof CtMethod) {
                        ut.usedMethods.add((CtMethod) executable);
                    } else if (executable instanceof CtConstructor) {
                        ut.usedConstructors.add((CtConstructor) executable);
                    } else {
                        System.err.println("What!?!");
                    }
                }
            } catch (SpoonClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
