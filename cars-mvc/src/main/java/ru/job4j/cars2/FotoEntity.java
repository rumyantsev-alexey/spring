package ru.job4j.cars2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Embeddable
@Entity
@NoArgsConstructor
public class FotoEntity extends  AbsProjectEntity {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Lob
    private byte[] foto;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private CarEntity car;
}
