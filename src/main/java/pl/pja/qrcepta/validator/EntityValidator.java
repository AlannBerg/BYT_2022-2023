package pl.pja.qrcepta.validator;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityValidator<U>{
    public boolean isValid(U classElementToCheckValidity) {
        log.info(
            "Check if {} is valid: {}",
            classElementToCheckValidity.getClass().getSimpleName(),
            classElementToCheckValidity);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<U>> constraintViolations =
            validator.validate(classElementToCheckValidity);
        if (constraintViolations.isEmpty()) {
            log.debug(
                "Given {} is valid: {}",
                classElementToCheckValidity.getClass().getSimpleName(),
                classElementToCheckValidity);
            return true;
        }
        StringBuilder errorLogBuilder = new StringBuilder();
        for (ConstraintViolation<U> constraintViolation : constraintViolations) {
            errorLogBuilder.append(constraintViolation.getPropertyPath().toString());
            errorLogBuilder.append(" ");
            errorLogBuilder.append(constraintViolation.getMessage());
            errorLogBuilder.append(", ");
        }
        log.debug(
            "Given {} is not valid: {}, reason: {}",
            classElementToCheckValidity.getClass().getSimpleName(),
            classElementToCheckValidity,
            errorLogBuilder.toString());
        return false;
    }
}