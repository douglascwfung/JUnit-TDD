package net.icestone.estore.service;

import net.icestone.estore.model.User;

public class UserServiceImpl implements UserService {
    @Override
    public void createUser() {
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        return new User(firstName, lastName, email);

    }
}
