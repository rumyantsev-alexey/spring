package ru.job4j.cars.models;

import ru.job4j.cars.dao.DbStore;

import java.util.Arrays;
import java.util.List;

public class InitFillValueDb {

    public void init() {
        this.fill(BodytypeEntity.class, Arrays.asList(new String[] {"sedan", "hetchbag", "limuzin", "offroad"}));
        this.fill(CityEntity.class, Arrays.asList(new String[] {"London", "Moscow", "Kiev"}));
        this.fill(DriveunitEntity.class, Arrays.asList(new String[] {"perednij", "zadnij", "polnij"}));
        this.fill(EnginestypeEntity.class, Arrays.asList(new String[] {"benzin", "disel", "hybrid", "electro", "gaz"}));
        this.fill(MarkEntity.class, Arrays.asList(new String[] {"Toyota", "VAZ", "Mersedes"}));
        this.fill(WheelEntity.class, Arrays.asList(new String[] {"left", "right"}));
        this.fill(TransmissionEntity.class, Arrays.asList(new String[] {"mechanic", "automat", "robot", "variator"}));
        DbStore<UsersEntity> dbu = new DbStore<>(UsersEntity.class);
        dbu.add(new UsersEntity("admin", "123", "djdj@jfj.ru"));
        DbStore<MarkEntity> dbm = new DbStore<>(MarkEntity.class);
        DbStore<ModelEntity> dbmd = new DbStore<>(ModelEntity.class);
        for (MarkEntity mrk: dbm.findAll()) {
            dbmd.add(new ModelEntity("ModelT" + mrk.getId(), mrk));
            dbmd.add(new ModelEntity("ModelA" + mrk.getId(), mrk));
            dbmd.add(new ModelEntity("ModelC" + mrk.getId(), mrk));
        }

    }

    private void  fill(Class<? extends AbsProjectEntity> genclass, List<String> values) {
        DbStore db = new DbStore(genclass);
        for (String val: values) {
            try {
                db.add(genclass.getConstructor(String.class).newInstance(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
