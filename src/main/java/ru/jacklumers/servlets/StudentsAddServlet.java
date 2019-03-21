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
import java.util.Properties;

/**
 * Сервлет для добавления учеников
 */
@WebServlet("/students/add")
public class StudentsAddServlet extends HttpServlet {

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
        req.getServletContext().getRequestDispatcher("/jsp/studentsAdd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Сохранение ученика
        saveStudentUsingParameters(req);
        resp.sendRedirect(req.getContextPath() + "/students/add");
    }

    /**
     * Сохранение ученика, используя параметры запроса
     *
     * @param req - запрос с параметрами
     */
    private void saveStudentUsingParameters(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String houseNum = req.getParameter("houseNum");
        String corps = req.getParameter("corps");
        String apartmentNum = req.getParameter("apartmentNum");
        String learningRateAsString = req.getParameter("learningRate");

        //Проверка, можно ли запарсить коэффициент успеваемсти во float.
        // TODO: Обработка и вывод ошибок заполнения формы
        float learningRate = 1.0f;
        try {
            learningRate = Float.parseFloat(learningRateAsString);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        studentsDao.save(new Student(firstName, secondName, phone, city, street, houseNum, corps, apartmentNum, learningRate));
    }
}
