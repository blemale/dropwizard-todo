package fr.blemale.dropwizard.todo.core;

import com.google.common.base.Preconditions;

public class User {
    private final String username;

    private User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public static class Builder {
        private String username;

        private Builder(String username) {
            this.username = username;
        }

        public static Builder anUser(String username) {
            return new Builder(username);
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public User build() {
            Preconditions.checkNotNull(this.username, "username can't be null");

            User user = new User(username);
            return user;
        }
    }
}
