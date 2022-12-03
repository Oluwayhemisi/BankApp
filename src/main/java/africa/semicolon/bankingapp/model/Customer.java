package africa.semicolon.bankingapp.model;


import africa.semicolon.bankingapp.model.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;


    private String lastName;

    @Email
    private String email;


    private String phoneNumber;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate = LocalDateTime.now();


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<RoleType> roles;

    private String password;

    private boolean isVerified;


    public Customer(String firstName, String lastName, String email, String password, String phoneNumber, RoleType roleType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        if(roles == null){
            roles = new HashSet<>();
        }
        roles.add(roleType);
    }
}
