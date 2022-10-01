package africa.semicolon.bankingapp.model;


import africa.semicolon.bankingapp.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @Column
    @SequenceGenerator(name= "role_id_sequence",
    sequenceName = "role_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "role_id_sequence"
    )
    private Long id;


    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role(RoleType roleType) { this.roleType = roleType;

    }

    public Role() {

    }

}
