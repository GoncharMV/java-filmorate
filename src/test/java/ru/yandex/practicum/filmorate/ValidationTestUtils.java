package ru.yandex.practicum.filmorate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationTestUtils {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    public static <T> boolean errorMessage(T dto, String message) {
        Set<ConstraintViolation<T>> validate = validator.validate(dto);
        return validate.stream().map(ConstraintViolation::getMessage).anyMatch(message::equals);
    }

}
