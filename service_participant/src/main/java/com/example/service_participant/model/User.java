package  com.example.service_participant.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;  // Utilisation d'un String car MongoDB génère un ObjectId
    private String firstName;
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is mandatory")
    @Indexed(unique = true)
    private String email;
    
}
