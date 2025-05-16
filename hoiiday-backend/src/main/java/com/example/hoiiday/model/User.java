package com.example.hoiiday.model;

import com.example.hoiiday.model.enums.UserRole;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "Users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column (name = "email")
    private String email;

    @Column (name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
    private Admin admin;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_logout_at")
    private LocalDateTime lastLogoutAt;

    public void markLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
    public void markLogout() {
        this.lastLogoutAt = LocalDateTime.now();
    }

    public User(Long userId, String password, UserRole role, String firstName, String lastName, String email, String phoneNumber, LocalDateTime lastLoginAt, LocalDateTime lastLogoutAt) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdDate = LocalDate.now();
        this.lastLoginAt = lastLoginAt;
        this.lastLogoutAt = lastLogoutAt;
    }

}
