package br.com.denys.domain;

import br.com.denys.dto.UserDTO;
import br.com.denys.responseDto.UserResponseDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_USER")
@SequenceGenerator(name = "user", sequenceName = "SQ_USER", allocationSize = 1, initialValue = 1)
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
    @Column(name = "cd_user")
    private Long id;

    @Column(name = "ds_name")
    private String name;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_password")
    private String password;

    @Column(name = "cd_task")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
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