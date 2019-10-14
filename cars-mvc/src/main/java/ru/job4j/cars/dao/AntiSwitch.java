package ru.job4j.cars.dao;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Класс реализующий механизм AntiSwitch
 */
public class AntiSwitch {
    private HashMap<String, Function<List<String>, String>> list = new HashMap<>();

    /**
     * Метод реализует сохранение значения и соответствующего ему действия
     * @param choose значение
     * @param action действие
     */
    public void load(String choose, Function<List<String>, String> action) {
        list.put(choose, action);
    }

    /**
     * Метод реализует выполнение действия, соответствующего значению
     * @param choose значение
     * @return успех
     */
    public String run(String choose, List<String> param) {
        return list.get(choose).apply(param);
    }

}
