package ru.job4j.cars.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class BodytypeEntity extends AbsProjectEntity {

    @Getter
    @Setter
    @OneToMany(mappedBy = "btype", fetch = FetchType.EAGER)
    private Set<CarEntity> car = new HashSet<>();

    public BodytypeEntity(String name) {
        super.setName(name);
    }
}
