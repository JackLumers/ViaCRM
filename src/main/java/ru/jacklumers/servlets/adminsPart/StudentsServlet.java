package ru.jacklumers.servlets.adminsPart;

import ru.jacklumers.dao.adminsPartDao.StudentsDao;
import ru.jacklumers.dao.adminsPartDao.StudentsDaoJdbcTemplateImpl;
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
@WebServlet("/adminsPart/students")
public class StudentsServlet extends HttpServlet {

    private StudentsDao studentsDao;

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties sysProperties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(sysProperties);
        //Инициализация StudentsDao интерфейса для работы с объектами учеников
        studentsDao = new StudentsDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students;

        /* Возврат студентов зависит от параметров запроса.
           Если параметры поиска есть, то выводит только студентов,
           найденных по этим параметрам */
        //TODO: Сделать валидацию поиска по коэффициенту успеваемости
        Enumeration<String> parametersNames = req.getParameterNames();
        Map<String, String> columnsAndArgs = new HashMap<>();
        if (parametersNames.hasMoreElements()) { //Если у запроса есть параметры
            while (parametersNames.hasMoreElements()) {
                String parameterName = parametersNames.nextElement();
                String arg = req.getParameter(parameterName);
                if (arg != null && !arg.isEmpty()) {
                    req.setAttribute(parameterName, arg); // Чтобы во вью пользователя введенные параметры сохранялись
                    columnsAndArgs.put(parameterName, arg);
                }
            }
        }
        if (!columnsAndArgs.isEmpty()) {
            students = studentsDao.findAllWithOnlySelfAttributesByArguments(columnsAndArgs);
        } else
            students = studentsDao.findAll();

        req.setAttribute("studentsFromServer", students);
        req.getServletContext().getRequestDispatcher("/jsp/adminsPart/students.jsp").forward(req, resp);
    }
}
