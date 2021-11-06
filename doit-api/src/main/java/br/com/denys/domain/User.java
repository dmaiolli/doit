package br.com.denys.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "TB_USER")
@SequenceGenerator(name = "user", sequenceName = "SQ_USER", allocationSize = 1, initialValue = 1)
public class User {

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
}
