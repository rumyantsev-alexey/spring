package ru.job4j.cars.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import ru.job4j.cars.models.*;
import ru.job4j.cars.services.*;

@Component
public class InitFillValueDb {
    private static final String[] BODY = new String[] {"sedan", "hetchbag", "limuzin", "offroad"};
    private static final String[] CITY = new String[] {"London", "Moscow", "Kiev"};
    private static final String[] DRIVE = new String[] {"perednij", "zadnij", "polnij"};
    private static final String[] ENGINE = new String[] {"benzin", "disel", "hybrid", "electro", "gaz"};
    private static final String[] MARK = new String[] {"Toyota", "VAZ", "Mersedes"};
    private static final String[] WHEEL = new String[] {"left", "right"};
    private static final String[] TRANS = new String[] {"mechanic", "automat", "robot", "variator"};


    @Autowired
    UserService userdb;
    @Autowired
    MarkService mdb;
    @Autowired
    ModelService mddb;
    @Autowired
    CityService citydb;
    @Autowired
    TransmissionService trdb;
    @Autowired
    BodytypeService bddb;
    @Autowired
    EnginestypeService edb;
    @Autowired
    DriveunitService drdb;
    @Autowired
    WheelService wdb;
    @Autowired
    FotoService fdb;

    public void init() {
        for (var i = 0; i < BODY.length; i++) {
            bddb.add(new BodytypeEntity(BODY[i]));
        }
        for (var i = 0; i < CITY.length; i++) {
            citydb.add(new CityEntity(CITY[i]));
        }
        for (var i = 0; i < DRIVE.length; i++) {
            drdb.add(new DriveunitEntity(DRIVE[i]));
        }
        for (var i = 0; i < ENGINE.length; i++) {
            edb.add(new EnginestypeEntity(ENGINE[i]));
        }
        for (var i = 0; i < MARK.length; i++) {
            mdb.add(new MarkEntity(MARK[i]));
        }
        for (var i = 0; i < WHEEL.length; i++) {
            wdb.add(new WheelEntity(WHEEL[i]));
        }
        for (var i = 0; i < TRANS.length; i++) {
            trdb.add(new TransmissionEntity(TRANS[i]));
        }
        userdb.add(new UsersEntity("admin", "123", "djdj@jfj.ru"));
        for (MarkEntity mrk: mdb.findAll()) {
            mddb.add(new ModelEntity("ModelT" + mrk.getId(), mrk));
            mddb.add(new ModelEntity("ModelA" + mrk.getId(), mrk));
            mddb.add(new ModelEntity("ModelC" + mrk.getId(), mrk));
        }
    }
}
