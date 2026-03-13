package keycloak;

import org.keycloak.validate.ValidationError;
import org.keycloak.validate.Validator;
import org.keycloak.validate.ValidationContext;
import org.keycloak.validate.ValidatorConfig;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class MinimumAgeValidator implements Validator {

    @Override
    public ValidationContext validate(Object input,
                                      String inputHint,
                                      ValidationContext context,
                                      ValidatorConfig config) {

        if (input == null) {
            return context;
        }

        try {

            String value;

            if (input instanceof java.util.Collection<?>) {
                value = ((java.util.Collection<?>) input).iterator().next().toString();
            } else {
                value = input.toString();
            }

            value = value.trim();

            if (value.length() >= 10) {
                value = value.substring(0, 10);
            }

            LocalDate dob = LocalDate.parse(value);

            int age = Period.between(dob, LocalDate.now()).getYears();

            Integer minAgeConfig = config.getInt("minAge");
            int minAge = minAgeConfig != null ? minAgeConfig : 16;

            if (age < minAge) {
                context.addError(new ValidationError(
                        "minimum-age",
                        inputHint,
                        "minimumAgeError",
                        List.of(minAge)
                ));
            }

        } catch (Exception e) {
            context.addError(new ValidationError(
                    "minimum-age",
                    inputHint,
                    "invalidDateFormat"
            ));
        }

        return context;
    }
}