package ru.job4j.cars.dao;

import ru.job4j.cars.models.CarEntity;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Класс реализует фильтр применяемые к списку объявдений
 */
public class CarsDbStore extends DbStore<CarEntity> {
    private final AntiSwitch answ = new AntiSwitch();

    /**
     * Конструктор создает объект доступа к базе объявоений и инициализирует фильтры
     */
    public CarsDbStore() {
        super(CarEntity.class);
        answ.load("actual", this.actual());
        answ.load("lastday", this.lastday());
        answ.load("withfoto", this.withfoto());
        answ.load("selectmark", this.selectmark());
    }

    /**
     * Метод реализует получение списка объявлений с примененными фильтрами
     * @param filters вписок параметров фильтра
     * @return списрк объявлений
     */
    public List<CarEntity> findAllCarsWithFilter(final List<String> filters) {
        return super.tx(
                session -> {
                    String qr = "FROM CarEntity C " + answ.run(filters.get(0), filters) + " order by C.id";
                    Query query = session.createQuery(qr);
                    return (List<CarEntity>) query.getResultList();
                }
        );
    }


    /**
     * Функция реализующая фильтр актуальности объявлений
     * @return
     */
    private Function<List<String>, String> actual() {
        return (params) ->    "where C.old IS NOT TRUE";
    }

    /**
     * Функция реализующая фильтр объявлений за сегодня
     * @return
     */
    private Function<List<String>, String> lastday() {
        return (params) ->      {
                                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                                    Date date = new Date(ts.getTime());
                                    Date todayWithZeroTime = null;
                                    try {
                                        todayWithZeroTime = formatter.parse(formatter.format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    ts = new Timestamp(todayWithZeroTime.getTime());
                                    return "where C.created > '" + ts.toString() + "'";
                                };
    }

    /**
     * Функция реализующая фильтр объявлений с фото
     * @return
     */
    private Function<List<String>, String> withfoto() {
        return (params) ->   "where C.fotos IS NOT EMPTY";
    }

    /**
     * Функция реализующая фильтр объявлений с определенной маркой автомобиля
     * @return
     */
    private Function<List<String>, String> selectmark() {
        return (params) ->    "where C.mark.name='" + params.get(1) + "'";
    }
}
