package fr.blemale.dropwizard.todo.auth;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;
import fr.blemale.dropwizard.todo.core.User;


public class DummyAuthenticator implements Authenticator<BasicCredentials, User> {
    private final String password;

    public DummyAuthenticator(String password) {
        this.password = password;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (password.equals(credentials.getPassword())) {
            return Optional.of(User.Builder.anUser(credentials.getUsername()).build());
        }
        return Optional.absent();
    }
}