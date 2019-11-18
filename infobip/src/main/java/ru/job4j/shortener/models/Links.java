package ru.job4j.shortener.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Links {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String source;

    @Column
    private String result;

    @Column
    private Integer count;

    @ManyToOne
    private User user;

}
