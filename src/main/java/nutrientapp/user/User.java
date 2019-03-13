package nutrientapp.user;

import org.springframework.data.annotation.Id;


public class User {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String emailAddress;

}
