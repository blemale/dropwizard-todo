package fr.blemale.dropwizard.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotBlank;

public class TodoConfiguration extends Configuration {
    @NotBlank
    @JsonProperty
    private final String password;

    public TodoConfiguration(@JsonProperty("password") String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
