import net.icestone.estore.model.User;
import net.icestone.estore.service.UserService;
import net.icestone.estore.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUsersObject(){
        // Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "Douglas";
        String lastName = "Fung";
        String email = "test@tes.com";
        String password = "12345678";
        String repeatPassword = "12345678";

        // Act
        User user = userService.createUser( firstName, lastName, email, password, repeatPassword );

        // Assert
        assertNotNull(user, "The createUser() should not have returned null");
        assertEquals(firstName, user.getFirstName(),"User's first name is incorrect.");
        assertEquals(lastName, user.getLastName(),"User's first name is incorrect.");
        assertEquals(email, user.getEmail(),"User's first name is incorrect.");

    }

}
