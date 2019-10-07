package ru.job4j.cars2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class CarEntity extends AbsProjectEntity {

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private UsersEntity user;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private CityEntity city;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MarkEntity mark;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private ModelEntity model;

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private Integer issue;

    @Getter
    @Setter
    private Integer dist;

    @Getter
    @Setter
    private Integer enginecapacity;

    @Getter
    @Setter
    private Integer power;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private TransmissionEntity trans;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private BodytypeEntity btype;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private EnginestypeEntity etype;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private DriveunitEntity dunit;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private WheelEntity wheel;

    @Getter
    @Setter
    private Timestamp created;

    @Getter
    @Setter
    private boolean old;

    @Getter
    @Setter
//    @Embedded
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<FotoEntity> fotos = new HashSet<>();

    public CarEntity(final String note) {
        super.setName(note);
        this.old = false;
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public String getNote() {
        return super.getName();
    }

    public void setNote(String note) {
        super.setName(note);
    }

}
