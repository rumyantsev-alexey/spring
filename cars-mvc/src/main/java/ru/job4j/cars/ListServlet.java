package ru.job4j.cars;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет работате со списком объявдений
 */
public class ListServlet extends HttpServlet {

    /**
     * Метод получает список объявлений и отображает их
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore<CarEntity> cdb = new DbStore<>(CarEntity.class);
        DbStore<MarkEntity> mdb = new DbStore<>(MarkEntity.class);
        req.setAttribute("carlist", cdb.findAll());
        req.setAttribute("MarkEntityList", mdb.findAll());
        req.getRequestDispatcher("/cars/list.jsp").forward(req, resp);
    }

    /**
     * Метод получает список моделей по марке автомобиля (на странице добавления объявления),
     * а также обрабатывает фильтр списка объявлений
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonData = req.getReader().lines().collect(Collectors.joining());
        PrintWriter out = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonData);
        String action = actualObj.get("action").textValue();
        if (action.equals("mark")) {
            String mark = actualObj.get("mark").textValue();
            DbStore<ModelEntity> db = new DbStore<>(ModelEntity.class);
            List<ModelEntity> mlist = db.findAll();
            ArrayList<String> result = new ArrayList<>();
            for (ModelEntity mdl : mlist) {
                if (mark.equals(mdl.getMark().getName())) {
                    result.add(mdl.getName());
                }
            }
            mapper.writeValue(out, result);
        } else {
            List<String> params = new ArrayList<>();
            params.add(action);
            params.add(actualObj.get("mark").textValue());
            CarsDbStore cdb = new CarsDbStore();
            List result = cdb.findAllCarsWithFilter(params);
            req.setAttribute("carlist", result);
            req.getRequestDispatcher("/cars/table.jsp").forward(req, resp);
        }
    }
}
