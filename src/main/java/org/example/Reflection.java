package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Reflection {

    public static void printClassInfo(Class<?> clazz) {
        try {
            // Extract fields
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("Field: " + field.getName());
                System.out.println("Type: " + field.getType());
                System.out.println("Modifiers: " + Modifier.toString(field.getModifiers()));
            }

            // Extract constructors
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println("Constructor: " + constructor.getName());
                System.out.println("Parameter types: " + Arrays.toString(constructor.getParameterTypes()));
                System.out.println("Modifiers: " + Modifier.toString(constructor.getModifiers()));
            }

            // Extract methods
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("Method: " + method.getName());
                System.out.println("Return type: " + method.getReturnType());
                System.out.println("Parameter types: " + Arrays.toString(method.getParameterTypes()));
                System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object createInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object callMethod(Object instance, String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true); // If the method is private
            return method.invoke(instance, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void executeReflectionDemo(Class<?> clazz) {
        // Print class information
        System.out.println("## Class Information:");
        printClassInfo(clazz);

        // Create an instance
        System.out.println("\n## Creating Instance:");
        Object instance = createInstance(clazz);
        if (instance != null) {
            System.out.println("Instance created successfully: " + instance);
        }

        // Call methods
        System.out.println("\n## Calling Methods:");

        // Print current staff
        callMethod(instance, "printCurrentStaff", new Class<?>[]{});
    }

}
