package ru.jacklumers.servlets.adminsPart;

import ru.jacklumers.dao.adminsPartDao.TeachersDao;
import ru.jacklumers.dao.adminsPartDao.TeachersDaoJdbcTemplateImpl;
import ru.jacklumers.models.Teacher;
import ru.jacklumers.utils.DataSourceBuilder;
import ru.jacklumers.utils.PropertiesLoader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Сервлет для добавления преподавателей
 * <p>
 * Объявление сервлета и маппинг
 * доступны через аннотации, благодаря Servlet API 3.1
 */
@WebServlet("/adminsPart/teachers/add")
public class TeachersAddServlet extends HttpServlet {

    private TeachersDao teachersDao;

    /**
     * Вызывается контейнером сервлетов (Tomcat),
     * в момент когда он загружает данный сервлет.
     */
    @Override
    public void init() throws ServletException {
        Properties properties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(properties);
        teachersDao = new TeachersDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/adminsPart/teachersAdd.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        saveTeacherUsingParameters(req);
        resp.sendRedirect(req.getContextPath() + "/adminsPart/teachers/add");
    }

    /**
     * Сохранение преподавателя, используя параметры запроса
     *
     * @param req - запрос с параметрами
     */
    private void saveTeacherUsingParameters(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        teachersDao.save(new Teacher(firstName, lastName));
    }
}


