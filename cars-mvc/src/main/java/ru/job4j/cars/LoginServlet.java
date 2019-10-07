package ru.job4j.cars;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cars/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore<UsersEntity> userdb = new DbStore<>(UsersEntity.class);
        HttpSession ses = req.getSession();
        PrintWriter out = resp.getWriter();
        UsersEntity user = new UsersEntity(req.getParameter("login"), req.getParameter("pass"), null);
        if (!user.getName().equals("anonymus")) {
            user = userdb.findById(userdb.findIdByModel(user));
        }
        ses.removeAttribute("loginUser");
        if (user != null) {
            ses.setAttribute("loginUser", user);
        }
        resp.sendRedirect("/cars/list");
    }
}
