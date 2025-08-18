package com.edelala.mur.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.List;
import com.edelala.mur.entity.Role;

//@Entity
//@Table(name = "users")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    // --- ADD THIS FIELD ---
//    @Column(unique = true, nullable = false) // Add constraints as needed
//    private String username;
//    // --- END ADDITION ---
//    private String firstName;
//    private String lastName;
//    @Column(unique = true)
//    private String email;
//    private String password;
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//}  jun 6

//@Entity
//@Data // Lombok for getters, setters, equals, hashCode, toString
//@NoArgsConstructor // Lombok for no-arg constructor
//@AllArgsConstructor // Lombok for all-arg constructor
//@Table(name = "users") // Explicitly name the table to avoid conflicts with 'user' keyword in some DBs
//
//// CRITICAL FIX: Ignore Hibernate's internal proxy properties during JSON serialization
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // --- Added and configured username field ---
//    @Column(unique = true, nullable = false)
//    private String username;
//    // --- End of username field addition ---
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    @JsonIgnore // Exclude password from JSON serialization for security
//    private String password;
//
//    private String firstName;
//    private String lastName;
//
//    @Enumerated(EnumType.STRING) // Store enum as String in DB
//    @Column(nullable = false)
//    private Role role; // RENTER, OWNER, ADMIN
//
//    // --- UserDetails interface methods ---
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    // Lombok's @Data annotation automatically generates getters and setters for all fields.
//    // If you are not using Lombok, you would need to manually include them here:
//    // public Long getId() { return id; }
//    // public void setId(Long id) { this.id = id; }
//    // ... and so on for all fields.
//}
//jun 13 10:52 pm

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    @JsonIgnore
//    private String password;
//
//    private String firstName;
//    private String lastName;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    // --- UserDetails interface methods ---
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // FIX: Ensure "ROLE_" prefix is added to the role name
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
////    public enum Role {
////        RENTER, OWNER, ADMIN
////    }
//}

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    @JsonIgnore
//    private String password;
//
//    private String firstName;
//    private String lastName;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); // Ensure "ROLE_" prefix
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
////    public enum Role {
////        RENTER, OWNER, ADMIN
////    }
//}
// jul 12

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    @JsonIgnore
//    private String password;
//
//    private String firstName;
//    private String lastName;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role; // Correctly referencing the standalone Role enum
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // This is correct: it adds the "ROLE_" prefix to the enum name.
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email; // Using email as username for authentication
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
//comment july 19 to fix edit profile

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Keep this if already present
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore // This is correct, keeps password out of JSON responses
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Correctly referencing the standalone Role enum

    @Override
    @JsonIgnore // <--- ADD THIS ANNOTATION HERE to prevent deserialization issues
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This is correct: it adds the "ROLE_" prefix to the enum name.
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Using email as username for authentication
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


