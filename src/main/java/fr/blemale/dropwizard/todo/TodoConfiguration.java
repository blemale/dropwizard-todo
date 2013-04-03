package fr.blemale.dropwizard.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TodoConfiguration extends Configuration {
    @NotBlank
    @JsonProperty
    private final String password;

    @Valid
    @NotNull
    @JsonProperty("database")
    private final DatabaseConfiguration databaseConfiguration;

    public TodoConfiguration(@JsonProperty("password") String password, @JsonProperty("database") DatabaseConfiguration databaseConfiguration) {
        this.password = password;
        this.databaseConfiguration = databaseConfiguration;
    }

    public String getPassword() {
        return password;
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }
}
