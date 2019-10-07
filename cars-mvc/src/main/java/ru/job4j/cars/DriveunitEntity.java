package ru.job4j.cars;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class DriveunitEntity extends AbsProjectEntity {

    @Getter
    @Setter
    @OneToMany(mappedBy = "dunit", fetch = FetchType.EAGER)
    private Set<CarEntity> car = new HashSet<>();

    public DriveunitEntity(String name) {
        super.setName(name);
    }
}
