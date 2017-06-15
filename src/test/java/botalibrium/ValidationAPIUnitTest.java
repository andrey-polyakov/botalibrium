package botalibrium;

import botalibrium.entity.Batch;
import botalibrium.entity.embedded.PlantMaterial;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static junit.framework.TestCase.assertEquals;


public class ValidationAPIUnitTest {

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Test
    public void testBatchWithNoValues() {
        Batch batch = new Batch();
        // validate the input
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Batch>> violations = validator.validate(batch);
        assertEquals(1, violations.size());

        Set<ConstraintViolation<PlantMaterial>> violations2 = validator.validate(new PlantMaterial());
        assertEquals(1, violations2.size());
    }

    @Test
    public void testPlantMaterialWithNoValues() {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PlantMaterial>> violations2 = validator.validate(new PlantMaterial());
        assertEquals(2, violations2.size());
    }
}
