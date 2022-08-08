package net.icestone.estore.service;

import java.util.UUID;

import net.icestone.estore.data.UsersRepository;
import net.icestone.estore.exception.UserServiceException;
import net.icestone.estore.model.User;

public class UserServiceImpl implements UserService {
	
	UsersRepository usersRepository;
	EmailVerificationService emailVerificationService;
	
	public UserServiceImpl(UsersRepository usersRepository, EmailVerificationService emailVerificationService) {
		this.usersRepository = usersRepository;
		this.emailVerificationService = emailVerificationService;
	}
	
	
    @Override
    public void createUser() {
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
    	
    	
    	if ( firstName == null || firstName.trim().length() == 0 ) {
    		throw new IllegalArgumentException("User's first name is empty");}
    	
    	if ( lastName == null || lastName.trim().length() == 0 ) {
    		throw new IllegalArgumentException("User's last name is empty");}
    	
    	String id = UUID.randomUUID().toString();
    	
    	User user = new User(id, firstName, lastName, email);
    	
    	boolean isUserCreated;
    	
    	try {

    		isUserCreated = usersRepository.save(user);
    	} catch (RuntimeException ex) {
    		throw new UserServiceException(ex.getMessage()); 
    	}    	
    	
    	System.out.println("isUserCreated:"+ isUserCreated);
    	
    	if (!isUserCreated) throw new UserServiceException("Could not create user");
    	

    	try {
    		emailVerificationService.scheduleEmailConfirmation(user);
    	} catch (RuntimeException ex) {
    		throw new UserServiceException(ex.getMessage()); 
    	}    	
    	    	
    	
    	
    	
        return user;

    }
}
