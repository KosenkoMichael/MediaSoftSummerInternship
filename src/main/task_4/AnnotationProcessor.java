import java.lang.reflect.Method;

public class AnnotationProcessor {
    public static void processDeprecatedEx(Class<?> cl) {
        if (cl.isAnnotationPresent(DeprecatedEx.class)) {
            DeprecatedEx annotation = cl.getAnnotation(DeprecatedEx.class);
            System.out.println("Warning: class " + cl.getSimpleName() + " is deprecated. Use " + annotation.message()
                    + " instead.");
        }

        for (Method method : cl.getDeclaredMethods()) {
            if (method.isAnnotationPresent(DeprecatedEx.class)) {
                DeprecatedEx annotation = cl.getAnnotation(DeprecatedEx.class);
                System.out.println("Warning: method " + method.getName() + " is deprecated. Use " + annotation.message()
                        + " instead.");
            }
        }
    }
}