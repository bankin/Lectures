package orm.scanner;

import annotations.Entity;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class EntityScanner {
    // FIXME: Instantiate classes from within packages
    public static List<Class> getAllEntities(String startPath) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Class> result = new ArrayList<>();

        File dir = new File(startPath);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (!fileName.endsWith(".class")) {
                    continue;
                }

                try {
                    Class myClass = Class.forName(fileName.substring(0, fileName.length() - 6));
//                    myClass.newInstance();
//
//                    if(!myClass.isAnnotationPresent(Entity.class)){
//                        continue;
//                    }

                    Constructor[] declaredConstructors = myClass.getDeclaredConstructors();

                    if (declaredConstructors.length == 0) {
                        continue;
                    }

                    Object o = declaredConstructors[0].newInstance();

                    result.add(o.getClass());
                }
                catch (ClassNotFoundException e) {
//                    System.out.println("Exception for " + fileName);
                }

            }
            else {
                List<Class> allEntities = getAllEntities(startPath + File.separator + file.getName());
                result.addAll(allEntities);
            }

        }

        return result;
    }
}
