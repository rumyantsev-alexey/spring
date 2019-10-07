package ru.job4j.cars2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет предносначет для отображения нужного объявления
 */
public class CarServlet extends HttpServlet {

    /**
     * Отображает объявление по id
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore<CarEntity> cdb = new DbStore<>(CarEntity.class);
        CarEntity car = cdb.findById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("curcar", car);
        req.getRequestDispatcher("/cars/car.jsp").forward(req, resp);
    }

    /**
     * Меняет статус объявдения
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("cid"));
        DbStore<CarEntity> cdb = new DbStore<>(CarEntity.class);
        CarEntity car = cdb.findById(id);
        car.setOld(!car.isOld());
        cdb.update(car);
        resp.sendRedirect("/cars/list");
    }
}
