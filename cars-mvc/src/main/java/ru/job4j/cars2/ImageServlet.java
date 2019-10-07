package ru.job4j.cars2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет возвращает фото по его id
 */
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fileid = Integer.parseInt(req.getParameter("cid"));
        DbStore<FotoEntity> fdb = new DbStore<>(FotoEntity.class);
        FotoEntity foto = fdb.findById(fileid);
        resp.setContentType("image/jpeg");
        resp.getOutputStream().write(foto.getFoto());
    }
}
