package ru.job4j.cars;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

/**
 * Сервлет обрабтывает добавление объявления
 */
@MultipartConfig
public class AddServlet extends HttpServlet {

    /**
     * Метод формирует возможные списки запчастей и формирует объявдение
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("new") != null) {
            findAll(CityEntity.class, req);
            findAll(MarkEntity.class, req);
            findAll(TransmissionEntity.class, req);
            findAll(BodytypeEntity.class, req);
            findAll(EnginestypeEntity.class, req);
            findAll(DriveunitEntity.class, req);
            findAll(WheelEntity.class, req);
            req.getRequestDispatcher("/cars/add.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/cars/list");
        }
    }

    /**
     * Метод собирет сущность Car и пытается ее записать в базу
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore<CarEntity> cardb = new DbStore<>(CarEntity.class);
        DbStore<CityEntity> citydb = new DbStore<>(CityEntity.class);
        DbStore<MarkEntity> markdb = new DbStore<>(MarkEntity.class);
        DbStore<ModelEntity> modeldb = new DbStore<>(ModelEntity.class);
        DbStore<TransmissionEntity> transdb = new DbStore<>(TransmissionEntity.class);
        DbStore<BodytypeEntity> btypedb = new DbStore<>(BodytypeEntity.class);
        DbStore<EnginestypeEntity> etypedb = new DbStore<>(EnginestypeEntity.class);
        DbStore<DriveunitEntity> dunitdb = new DbStore<>(DriveunitEntity.class);
        DbStore<WheelEntity> wheeldb = new DbStore<>(WheelEntity.class);
        DbStore<FotoEntity> fdb = new DbStore<>(FotoEntity.class);
        CarEntity car = new CarEntity(req.getParameter("note"));
        car.setUser((UsersEntity) req.getSession().getAttribute("loginUser"));
        car.setCity(citydb.findById(citydb.findIdByModel(new CityEntity(req.getParameter("city")))));
        car.setMark(markdb.findById(markdb.findIdByModel(new MarkEntity(req.getParameter("mark")))));
        car.setModel(modeldb.findById(modeldb.findIdByModel(new ModelEntity(req.getParameter("model"), car.getMark()))));
        car.setPrice(req.getParameter("price").isEmpty() ? 0 : Integer.parseInt(req.getParameter("price")));
        car.setIssue(req.getParameter("issue").isEmpty() ? 0 : Integer.parseInt(req.getParameter("issue")));
        car.setDist(req.getParameter("dist").isEmpty() ? 0 : Integer.parseInt(req.getParameter("dist")));
        car.setTrans(transdb.findById(transdb.findIdByModel(new TransmissionEntity(req.getParameter("trans")))));
        car.setBtype(btypedb.findById(btypedb.findIdByModel(new BodytypeEntity(req.getParameter("body")))));
        car.setEtype(etypedb.findById(etypedb.findIdByModel(new EnginestypeEntity(req.getParameter("engine")))));
        car.setEnginecapacity(req.getParameter("enginecapacity").isEmpty() ? 0 : Integer.parseInt(req.getParameter("enginecapacity")));
        car.setPower(req.getParameter("power").isEmpty() ? 0 : Integer.parseInt(req.getParameter("power")));
        car.setDunit(dunitdb.findById(dunitdb.findIdByModel(new DriveunitEntity(req.getParameter("drive")))));
        car.setWheel(wheeldb.findById(wheeldb.findIdByModel(new WheelEntity(req.getParameter("wheel")))));
        Set<FotoEntity> fotos = new HashSet<>();
        Collection<Part> reqlist = req.getParts();
        FotoEntity ft = null;
        for (Part prt: reqlist) {
            if (prt.getName().equals("upfile") && prt.getSize() > 0) {
                ft = new FotoEntity();
                ft.setName(prt.getSubmittedFileName());
                ft.setFoto(IOUtils.toByteArray(prt.getInputStream()));
                ft.setCar(car);
                fotos.add(ft);
            }
        }
        car.setFotos(fotos);
        if (!(cardb.add(car) > 0)) {
            resp.sendError(400);
        }
    }

    private void findAll(Class<? extends AbsProjectEntity> genclass, HttpServletRequest req) {
        DbStore db = new DbStore<>(genclass);
        List lst = db.findAll();
        req.setAttribute(db.getEntityname() + "List", lst);
    }

}
