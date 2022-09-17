package africa.semicolon.bankingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Bank {
    private Long id;
    private String name;
    private List<User> users;

    public Bank(Long id, String name) {
        this.id = id;
        this.name = name;
        users =  new ArrayList<>();
    }
}
