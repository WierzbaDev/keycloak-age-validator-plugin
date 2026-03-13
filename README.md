# Keycloak Minimum Age Validator

A custom validator plugin for Keycloak that enforces a minimum age
requirement during user registration.

The plugin validates a user attribute (e.g. `dateOfBirth`) and prevents
account creation if the user is younger than the configured minimum age.

This is useful for applications that must enforce legal or policy
requirements such as **minimum age 13/16/18**.

------------------------------------------------------------------------

## Features

-   Custom **Keycloak User Profile Validator**
-   Configurable **minimum age**
-   Works with **User Profile attributes**
-   Compatible with **Keycloak 24+**
-   Optional custom login theme for better validation messages

------------------------------------------------------------------------

## How It Works

During registration the validator reads the `dateOfBirth` attribute and
calculates the user's age.

If the user's age is lower than the configured minimum age, the
registration fails with a validation error.

Example:

    dateOfBirth = 2012-05-10
    minAge = 16

Result:

    Registration rejected – user is under the minimum age.

------------------------------------------------------------------------

## Installation

### 1. Build the Plugin

    mvn clean package

The JAR will be created in:

    target/keycloak-age-validator-1.0-0.jar

------------------------------------------------------------------------

### 2. Deploy to Keycloak

Copy the JAR to the Keycloak providers directory:

    /opt/keycloak/providers

Example:

    cp target/keycloak-age-validator-1.0-0.jar /opt/keycloak/providers/

------------------------------------------------------------------------

### 3. Rebuild Keycloak

Run:

    /opt/keycloak/bin/kc.sh build

Restart the server afterwards.

------------------------------------------------------------------------

## Docker Installation

This project includes a sample Dockerfile.

Build the image:

    docker build -f Dockerfile-keycloak -t keycloak-age-validator .

Run it using Docker or docker-compose.

------------------------------------------------------------------------

## Configuration

Add the validator to your **User Profile configuration**.

Example:

    {
      "name": "dateOfBirth",
      "displayName": "Date of birth",
      "validations": {
        "pattern": {
          "pattern": "\\d{4}-\\d{2}-\\d{2}",
          "error-message": "Please enter date in format YYYY-MM-DD"
        },
        "minimum-age": {
          "minAge": 16
        }
      }
    }

------------------------------------------------------------------------

## Token Mapping (Optional)

If you want to expose the birthdate in the JWT token:

1.  Go to **Clients → Client Scopes**
2.  Open your client scope
3.  Add mapper:
```
    Mapper Type: User Attribute
    User Attribute: dateOfBirth
    Token Claim Name: birthdate
```

The token will contain:
```
    {
      "birthdate": "2000-12-01"
    }
```
------------------------------------------------------------------------

## Custom Theme (Optional)

The repository contains a simple login theme used to display better
validation messages.

Location:

    themes/custom/login

To enable the theme:

    Realm Settings → Themes → Login Theme → custom

------------------------------------------------------------------------

## Project Structure

    src/main/java/keycloak
      MinimumAgeValidator.java
      ValidationFactory.java

    src/main/resources/META-INF/services
      org.keycloak.validate.ValidatorFactory

    themes/custom/login
      login.ftl
      messages.properties
      theme.properties

------------------------------------------------------------------------

## Example Validation Error

    You must be at least 16 years old to register

------------------------------------------------------------------------

## Future Improvements

Possible extensions:

-   configurable **date attribute name**
-   support for **different date formats**
-   automatic **age claim in token**
-   admin UI configuration for `minAge`

------------------------------------------------------------------------

## License

MIT License
