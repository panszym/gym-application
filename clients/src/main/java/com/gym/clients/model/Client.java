package com.gym.clients.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "FirstName must not be blank.")
    private String firstName;
    @NotBlank(message = "LastName must not be blank.")
    private String lastName;
    @NotBlank
    @Size(min = 5)
    private String password;
    @NotBlank(message = "Email must not be blank.")
    @Email
    private String email;

    @NotNull(message = "Status must not be null.")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Status must not be null.")
    @Enumerated(EnumType.STRING)
    private Ticket ticket;
    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateClientPut(Client client) {
        setFirstName(client.getFirstName());
        setLastName(client.getLastName());
        setEmail(client.getEmail());
        setStatus(client.getStatus());
        setTicket(client.getTicket());
    }

    public void updateClientPatch(Client client) {
        if (!StringUtils.isEmpty(client.getFirstName())) setFirstName(client.getFirstName());
        if (!StringUtils.isEmpty(client.getLastName())) setLastName(client.getLastName());
        if (client.getStatus() != null) setStatus(client.getStatus());
        if (client.getTicket() != null) setTicket(client.getTicket());
    }

    public void activateClient() {
        setRole(Role.CLIENT);
        setStatus(Status.ACTIVE);
    }

    public void toggleStatus() {
        if (Status.ACTIVE.equals(this.getStatus())) {
            setStatus(Status.INACTIVE);
        } else setStatus(Status.ACTIVE);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Ticket {
        NORMAL,
        PREMIUM,
        MASTER
    }
}

