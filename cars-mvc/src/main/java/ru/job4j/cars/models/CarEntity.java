package ru.job4j.cars.models;

import lombok.Data;
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
@Data
public class CarEntity extends AbsProjectEntity {

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private UsersEntity user;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private CityEntity city;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MarkEntity mark;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private ModelEntity model;

    private Integer price;

    private Integer issue;

    private Integer dist;

    private Integer enginecapacity;

    private Integer power;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private TransmissionEntity trans;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private BodytypeEntity btype;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private EnginestypeEntity etype;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private DriveunitEntity dunit;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private WheelEntity wheel;

    private Timestamp created;

    private boolean old;

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
