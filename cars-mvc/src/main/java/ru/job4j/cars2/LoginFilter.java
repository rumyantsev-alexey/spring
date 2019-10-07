package ru.job4j.cars2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginFilter  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        InitFillValueDb db = new InitFillValueDb();

        db.fill(BodytypeEntity.class, Arrays.asList(new String[] {"sedan", "hetchbag", "limuzin", "offroad"}));
        db.fill(CityEntity.class, Arrays.asList(new String[] {"London", "Moscow", "Kiev"}));
        db.fill(DriveunitEntity.class, Arrays.asList(new String[] {"perednij", "zadnij", "polnij"}));
        db.fill(EnginestypeEntity.class, Arrays.asList(new String[] {"benzin", "disel", "hybrid", "electro", "gaz"}));
        db.fill(MarkEntity.class, Arrays.asList(new String[] {"Toyota", "VAZ", "Mersedes"}));
        db.fill(WheelEntity.class, Arrays.asList(new String[] {"left", "right"}));
        db.fill(TransmissionEntity.class, Arrays.asList(new String[] {"mechanic", "automat", "robot", "variator"}));
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

    /**
     * Фильтр проверяет сформинрован ли пользователь в сессии
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession ses = req.getSession();
        String login = (String) req.getParameter("login");
        UsersEntity user = (UsersEntity) ses.getAttribute("loginUser");
        if (user == null && login == null) {
            req.getRequestDispatcher("/cars/login").forward(req, (HttpServletResponse) servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
