package ru.jacklumers.servlets.adminsPart;

import ru.jacklumers.dao.adminsPartDao.TeachersDao;
import ru.jacklumers.dao.adminsPartDao.TeachersDaoJdbcTemplateImpl;
import ru.jacklumers.models.Teacher;
import ru.jacklumers.utils.DataSourceBuilder;
import ru.jacklumers.utils.PropertiesLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

@WebServlet("/adminsPart/teachers")
public class TeachersServlet extends HttpServlet {

    private TeachersDao teachersDao;


    @Override
    public void init() throws ServletException {
        Properties properties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(properties);
        teachersDao = new TeachersDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> teachers;

        /* Возврат студентов зависит от параметров запроса

         * Название параметра = название колонки в базе данных
         * Значение параметра = искомое значение колонки в базе данных.
         *
         * По этим данным соответственно составляется HashMap,
         * с помощью которого составляется запрос в БД */
        Enumeration<String> parametersNames = req.getParameterNames();
        Map<String, String> columnsArgsHashMap = new HashMap<>();

        if (parametersNames.hasMoreElements()) { //Если у запроса есть параметры
            while (parametersNames.hasMoreElements()) {
                String parameterName = parametersNames.nextElement();
                String arg = req.getParameter(parameterName);
                if (arg != null && !arg.isEmpty()) { //Если значение параметра не пустое
                    columnsArgsHashMap.put(parameterName, arg);
                    req.setAttribute(parameterName, arg);
                }
            }
        }

        if (!columnsArgsHashMap.isEmpty()) { //Если HashMap не пустая
            teachers = teachersDao.findAllWithOnlySelfAttributesByArgs(columnsArgsHashMap);
        } else {
            teachers = teachersDao.findAll();
        }

        req.setAttribute("teachersFromServer", teachers);
        req.getServletContext().getRequestDispatcher("/jsp/adminsPart/teachers.jsp").forward(req, resp);
    }
}
