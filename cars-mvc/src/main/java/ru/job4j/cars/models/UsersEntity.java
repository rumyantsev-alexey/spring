package ru.job4j.cars.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"email", "city", "car"}, callSuper = true)
public class UsersEntity extends AbsProjectEntity {

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private CityEntity city;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<CarEntity> car = new HashSet<>();

    public UsersEntity(String name, String password, String email) {
        super.setName(name);
        this.password = password;
        this.email = email;
    }

}
