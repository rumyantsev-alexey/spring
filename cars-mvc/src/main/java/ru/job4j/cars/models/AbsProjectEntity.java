package ru.job4j.cars.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode(exclude = {"id"})
public abstract class AbsProjectEntity {

    @Id
    @GeneratedValue
    @Getter
    private int id;

    @Getter
    @Setter
    @NonNull
    @Column(name = "name")
    private String name;
}
