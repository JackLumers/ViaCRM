package ru.jacklumers.servlets.adminsPart;

import ru.jacklumers.dao.adminsPartDao.DatedLessonsDao;
import ru.jacklumers.dao.adminsPartDao.DatedLessonsJdbcTemplateImpl;
import ru.jacklumers.models.DatedLesson;
import ru.jacklumers.utils.DataSourceBuilder;
import ru.jacklumers.utils.PropertiesLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet("/adminsPart/datedLessons")
public class DatedLessonsServlet extends HttpServlet {

    private DatedLessonsDao datedLessonsDao;

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties sysProperties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(sysProperties);
        datedLessonsDao = new DatedLessonsJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DatedLesson> datedLessons = datedLessonsDao.findAll();
        req.setAttribute("datedLessonsFromServer", datedLessons);
        req.getServletContext().getRequestDispatcher("/jsp/adminsPart/datedLessons.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
