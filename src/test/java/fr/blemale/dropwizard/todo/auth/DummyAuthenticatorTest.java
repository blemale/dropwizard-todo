package fr.blemale.dropwizard.todo.auth;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.basic.BasicCredentials;
import fr.blemale.dropwizard.todo.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DummyAuthenticatorTest {
    private final String password = "pwd";

    private DummyAuthenticator dummyAuthenticator;

    @Before
    public void setUp() {
        this.dummyAuthenticator = new DummyAuthenticator(password);
    }

    @After
    public void cleanUp() {
        this.dummyAuthenticator = null;
    }

    @Test
    public void authenticateWithGoodCredentials() throws Exception {
        final String username = "user";
        final BasicCredentials credentials = new BasicCredentials(username, password);
        final User expectedUser = User.Builder.anUser(username).build();

        Optional<User> actualUser = this.dummyAuthenticator.authenticate(credentials);

        assertTrue("authenticate with valid credentials should return a user", actualUser.isPresent());
        assertThat("authenticate with valid credential should return the user with the given username", actualUser.get(), is(expectedUser));
    }

    @Test
    public void authenticateWithBadCredentials() throws Exception {
        final BasicCredentials credentials = new BasicCredentials("user", "bad-pwd");

        Optional<User> actualUser = this.dummyAuthenticator.authenticate(credentials);

        assertTrue("authenticate with bad credentials should return no user", !actualUser.isPresent());

    }
}
