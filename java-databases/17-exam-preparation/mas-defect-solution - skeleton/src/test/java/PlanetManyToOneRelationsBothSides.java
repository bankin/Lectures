//ManyToOne
import com.masdefect.domain.entities.Planet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlanetManyToOneRelationsBothSides {
    private Planet planet;

    @Before
    public void setUp() {
        this.planet = new Planet();
    }

    @Test
    public void testManyToOneRelations() throws Exception {
        Field[] photographerFields = this.planet.getClass().getDeclaredFields();
        List<Field> manyToOneFields = Arrays.stream(photographerFields)
                .filter(field -> field.isAnnotationPresent(ManyToOne.class)).collect(Collectors.toList());
        int counter = 0;
        for (Field manyToOneField : manyToOneFields) {
            Class<?> inverseField = manyToOneField.getType();
            for (Field field : inverseField.getDeclaredFields()) {
                if (field.isAnnotationPresent(OneToMany.class)) {
                    OneToMany oneToMany = field.getAnnotation(OneToMany.class);
                    String mappedBy = oneToMany.mappedBy();
                    if (mappedBy.equals(manyToOneField.getName())) {
                        counter++;
                    }
                }
            }
        }
        Assert.assertEquals(manyToOneFields.size(), counter - 1);
    }
}