package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String firstName;
    private String lastName ;
    private String username;
    private String password;
    @Lob

    private String image;
    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinTable(name = "admin_rules" ,joinColumns = @JoinColumn(name="admin_id",referencedColumnName = "admin_id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "role_id"))
    private Collection <Role> roles;
}
