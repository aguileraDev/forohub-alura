package com.alura.forohub.model;


import com.alura.forohub.dto.CreateUserDto;
import com.alura.forohub.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean isActive;

    private Instant lastLogin;

    @OneToMany(
            mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Topic> topics;


    public User(CreateUserDto createUserDto){
        this.name = createUserDto.name();
        this.email = createUserDto.email();
        this.password = createUserDto.password();
        this.isActive = true;
        try{
            this.lastLogin = ZonedDateTime.now(ZoneId.of("America/Bogota")).toInstant();
        }catch (DateTimeException e){
            this.lastLogin = Instant.now();
        }
    }

    public User(UserDto userDto) {
        this.id = userDto.id();
        this.name = userDto.name();
        this.isActive = userDto.isActive();
        this.lastLogin = userDto.login();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
