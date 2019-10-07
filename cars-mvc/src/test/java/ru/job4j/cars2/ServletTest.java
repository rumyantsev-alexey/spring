package ru.job4j.cars2;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.persistence.EntityManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Класс предназначен для тестирования функционирования контроллера на основе сервлетов
 */
public class ServletTest {

    private final EntityManagerFactory factory = EntityManagerFactorySigl.getEntityManagerFactory();
    private CarsDbStore carsdb = new CarsDbStore();

    /**
     * Метод тестирует сервлет AddServlet метод doGet (переход на страницу добавления объявления
     * при переходе из списка объявдений)
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDoGetAddServletLinkFromListCars() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher reqd = mock(RequestDispatcher.class);

        when(req.getParameter("new")).thenReturn("11");
        when(req.getRequestDispatcher("/cars/add.jsp")).thenReturn(reqd);

        new AddServlet().doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher("/cars/add.jsp");
        verify(resp, never()).sendRedirect("/cars/list");

    }

    /**
     * Метод тестирует сервлет AddServlet метод doGet (переход на страницу списка объявлений
     * при переходе с любого другого места, кроме страницы спсика объявлений)
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDoGetAddServletOnlyLink() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("new")).thenReturn(null);

        new AddServlet().doGet(req, resp);
        verify(req, never()).getRequestDispatcher("/cars/add.jsp");
        verify(resp, times(1)).sendRedirect("/cars/list");

    }

    /**
     * Метод тестирует сервлет AddServlet метод doPost (проверяем успех добавления объявления в базу)
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDoPostAddServletSuccessCarAdd() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession sess = mock(HttpSession.class);

        DbStore<UsersEntity> dbu = new DbStore<>(UsersEntity.class);
        int idx = dbu.add(new UsersEntity("test", "123", "wwww"));

        when(req.getSession()).thenReturn(sess);
        when(req.getParameter("note")).thenReturn("test");
        when(sess.getAttribute("loginUser")).thenReturn(dbu.findById(idx));
        when(req.getParameter("mark")).thenReturn("test");
        when(req.getParameter("city")).thenReturn("test");
        when(req.getParameter("model")).thenReturn("test");
        when(req.getParameter("price")).thenReturn("3456");
        when(req.getParameter("issue")).thenReturn("1999");
        when(req.getParameter("dist")).thenReturn("23456");
        when(req.getParameter("trans")).thenReturn("test");
        when(req.getParameter("body")).thenReturn("test");
        when(req.getParameter("engine")).thenReturn("test");
        when(req.getParameter("enginecapacity")).thenReturn("4321");
        when(req.getParameter("power")).thenReturn("345");
        when(req.getParameter("drive")).thenReturn("test");
        when(req.getParameter("wheel")).thenReturn("test");

        new AddServlet().doPost(req, resp);
        CarEntity car = new CarEntity("test");
        idx = carsdb.findIdByModel(car);
        assertTrue(idx > 0);
    }

    /**
     * Метод тестирует сервлет CarServlet метод doGet (проверяем успех получения объявления по id)
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDoGetCarServletGetCarById() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher reqd = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher("/cars/car.jsp")).thenReturn(reqd);

        CarEntity car = new CarEntity("test2");
        int idx = carsdb.add(car);

        when(req.getParameter("id")).thenReturn(Integer.toString(idx));

        ArgumentCaptor<Object> args = ArgumentCaptor.forClass(Object.class);

        new CarServlet().doGet(req, resp);

        verify(req, times(1)).setAttribute(anyString(), args.capture());
        CarEntity carresult = (CarEntity) args.getValue();
        verify(req, times(1)).getRequestDispatcher("/cars/car.jsp");
        assertTrue(car.equals(carresult));
    }

    /**
     * Метод тестирует сервлет AddServlet метод doPost (проверяем успех изменения статуса объявления)
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDoPostCarServletUpdateCarStatus() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        CarEntity car = new CarEntity("test3");
        int idx = carsdb.add(car);

        when(req.getParameter("cid")).thenReturn(Integer.toString(idx));

        new CarServlet().doPost(req, resp);

        CarEntity car2 = carsdb.findById(idx);
        assertTrue(car.isOld() != car2.isOld());
    }

    /**
     * Метод тестирует сервлет ListServlet метод doGet (проверяем успех получения всех объявлений из базу)
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDoGetListServletGetAllCars() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher reqd = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher("/cars/list.jsp")).thenReturn(reqd);

        carsdb.add(new CarEntity("test2"));
        carsdb.add(new CarEntity("test3"));
        carsdb.add(new CarEntity("test4"));

        ArgumentCaptor<Object> args = ArgumentCaptor.forClass(Object.class);

        new ListServlet().doGet(req, resp);

        verify(req, times(1)).setAttribute(eq("carlist"), args.capture());
        ArrayList<CarEntity> result = (ArrayList<CarEntity>) args.getValue();
        verify(req, times(1)).getRequestDispatcher("/cars/list.jsp");
        assertTrue(result.size() == 3);
    }

    @After
    public void afterDo() {
        List<CarEntity> all = carsdb.findAll();
        for (CarEntity c: all) {
            carsdb.delete(c.getId());
        }
    }

}