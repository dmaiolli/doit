package br.com.denys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_TASK")
@SequenceGenerator(name = "task", sequenceName = "SQ_TASK", initialValue = 1, allocationSize = 1)
public class Task {

    @Id
    @GeneratedValue(generator = "task", strategy = GenerationType.SEQUENCE)
    @Column(name = "cd_task")
    private Long id;

    @Column(name = "ds_title")
    private String title;

    @Column(name = "ds_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cd_user")
    private User user;

    public Task(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }
}
