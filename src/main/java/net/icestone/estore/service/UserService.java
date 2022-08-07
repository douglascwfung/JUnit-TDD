package net.icestone.estore.service;

import net.icestone.estore.model.User;

public interface UserService {
    void createUser();

     User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
