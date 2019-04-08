package ru.jacklumers.servlets.schoolworkConstructor;

import ru.jacklumers.utils.DataSourceBuilder;
import ru.jacklumers.utils.PropertiesLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/schoolworkConstructor/learningStages")
public class LearningStagesServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties sysProperties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(sysProperties);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext()
                .getRequestDispatcher("/jsp/schoolworkConstructor/learningStages.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
