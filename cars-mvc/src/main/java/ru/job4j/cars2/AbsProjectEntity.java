package ru.job4j.cars2;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.UniqueConstraint;

@MappedSuperclass
@EqualsAndHashCode(exclude = {"id"})
public abstract class AbsProjectEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @NonNull
    private String name;
}
