package com.ga.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"password","userProfile","categoryList","todoList"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @Column(unique = true)
    private String emailAddress;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    //oner user can have only one profile its a one to one relation
    @OneToOne(
            cascade = CascadeType.ALL,fetch = FetchType.LAZY
    )
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private UserProfile userProfile;
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Todo> todoList;

    //user can have more than one category
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Category> categoryList;

    private boolean accountVerified;

    @OneToMany(mappedBy = "user")
    private Set<SecureToken> token;

    @JsonIgnore
    public String getPassword(){
        return password;
    }

}
