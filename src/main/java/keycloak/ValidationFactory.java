package keycloak;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.validate.Validator;
import org.keycloak.validate.ValidatorFactory;

public class ValidationFactory implements ValidatorFactory {

    public static final String ID = "minimum-age";

    @Override
    public Validator create(KeycloakSession session) {
        return new MinimumAgeValidator();
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return ID;
    }
}
