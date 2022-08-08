package net.icestone.estore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.icestone.estore.data.UsersRepository;
import net.icestone.estore.exception.EmailNotificationException;
import net.icestone.estore.exception.UserServiceException;
import net.icestone.estore.model.User;
import net.icestone.estore.service.EmailVerificationServiceImpl;
import net.icestone.estore.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UsersRepository usersRepository;
	
	@Mock
	EmailVerificationServiceImpl emailVerificationService;
	
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

	
	@BeforeEach
	void init() {
		
        firstName = "Douglas";
        lastName = "Fung";
        email = "test@tes.com";
        password = "12345678";
        repeatPassword = "12345678";
		
	}

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUsersObject(){
        // Arrange
    	when(usersRepository.save(any(User.class))).thenReturn(true);

        // Act
        User user = userService.createUser( firstName, lastName, email, password, repeatPassword );

        // Assert
        assertNotNull(user, "The createUser() should not have returned null");
        assertEquals(firstName, user.getFirstName(),"User's first name is incorrect.");
        assertEquals(lastName, user.getLastName(),"User's last name is incorrect.");
        assertEquals(email, user.getEmail(),"User's email is incorrect.");
        assertNotNull(user.getId(), "User id is missing.");
        verify(usersRepository, times(1))
        	.save(any(User.class));
        
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstnameIsEmpty_throwsIllegalException(){
        // Arrange
        String firstName = "";
        String expectedExceptionMessage = "User's first name is empty";

        // Act
//        User user = userService.createUser( firstName, lastName, email, password, repeatPassword );

        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                // Act
                ()-> {userService.createUser( firstName, lastName, email, password, repeatPassword );
                }, "Division by zero should have thrown an Arithmetic exception.");
        
        assertEquals(expectedExceptionMessage, thrown.getMessage());

    }

    
    @DisplayName("Empty last name causes correct exception")
    @Test
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String lastName = "";
        String expectedExceptionMessage = "User's last name is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Empty last name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage,thrown.getMessage(),
                "Exception error message is not correct");
    }
    

    @DisplayName("If save() method causes RunTimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_throwsUserServicetException() {
        // Arrange

    	when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class) ;

    	
        // Act & Assert
        Assertions.assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Should have thrown UserServiceException");

        // Assert
//        assertEquals(expectedExceptionMessage,thrown.getMessage(),
//                "Exception error message is not correct");
        
    }
    
    @DisplayName("EmailNotificationException is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_thrownsUserServiceException() {

        // Arrange

    	when(usersRepository.save(any(User.class))).thenReturn(true);
    	doThrow(EmailNotificationException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
    	
//    	doNothing().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
    	
        // Act & Assert
        Assertions.assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Should have thrown UserServiceException instead");
        
        // Assert
        verify(emailVerificationService, times(1))
    	.scheduleEmailConfirmation(any(User.class));
    	
    }
    
    @DisplayName("Schedule Email Confirmation is executed")
    @Test
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation() {

        // Arrange
    	when(usersRepository.save(any(User.class))).thenReturn(true);
    	
    	doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
    	
        // Act & Assert
    	userService.createUser(firstName, lastName, email, password, repeatPassword);
        
        // Assert
        verify(emailVerificationService, times(1))
    	.scheduleEmailConfirmation(any(User.class));
    	
    	
    }  
    
    
    @Test
    public void TestDemo() {
    	System.out.println("do nothing");
    }
    
    
}
