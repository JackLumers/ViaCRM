package ru.jacklumers.servlets;

import ru.jacklumers.dao.StudentsDao;
import ru.jacklumers.dao.StudentsDaoJdbcTemplateImpl;
import ru.jacklumers.models.Student;
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

/**
 * Сервлет с выводом информации о всех учениках
 */
@WebServlet("/students")
public class StudentsServlet extends HttpServlet {

    private StudentsDao studentsDao;

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties properties = PropertiesLoader.loadProperties("config", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(properties);
        //Инициализация StudentsDao интерфейса для работы с объектами учеников
        studentsDao = new StudentsDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students;

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
            students = studentsDao.findAllByArgs(columnsArgsHashMap);
        } else {
            students = studentsDao.findAll();
        }

        req.setAttribute("studentsFromServer", students);
        req.getServletContext().getRequestDispatcher("/jsp/students.jsp").forward(req, resp);
    }
}
