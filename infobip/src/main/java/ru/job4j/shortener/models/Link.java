package ru.job4j.shortener.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String source;

    @Column
    private String result;

    @Column
    private int count;

    @ManyToOne (cascade = {CascadeType.REFRESH})
    private User user;

}
