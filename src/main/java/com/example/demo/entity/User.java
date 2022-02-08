package com.example.demo.entity;

import com.example.demo.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String middlename;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<ERole> role = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<FinanceRecord> financerecord = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(){

    }

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}