package gr.unipi.javaspot.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameExistenceValidator.class)
public @interface UniqueUsername {

    String message() default "Username already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
