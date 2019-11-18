package ru.job4j.shortener.models;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue()
    private long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    @OneToMany(mappedBy = "user")
    private List<Links> links;
}
