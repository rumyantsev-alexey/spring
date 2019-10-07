package ru.job4j.cars2;

import java.util.List;

public class InitFillValueDb {

    public void  fill(Class<? extends AbsProjectEntity> genclass, List<String> values) {
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
