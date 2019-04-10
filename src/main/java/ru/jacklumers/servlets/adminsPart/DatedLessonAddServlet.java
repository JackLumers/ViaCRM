package ru.jacklumers.servlets.adminsPart;

import ru.jacklumers.dao.adminsPartDao.*;
import ru.jacklumers.models.DatedLesson;
import ru.jacklumers.models.Student;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/adminsPart/datedLessons/add")
public class DatedLessonAddServlet extends HttpServlet {

    private DatedLessonsDao datedLessonsDao;
    private StudentsDao studentsDao;
    private TeachersDao teachersDao;

    @Override
    public void init() throws ServletException {
        Properties properties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(properties);
        datedLessonsDao = new DatedLessonsJdbcTemplateImpl(dataSource);
        studentsDao = new StudentsDaoJdbcTemplateImpl(dataSource);
        teachersDao = new TeachersDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> studentFullNames = new ArrayList<>();
        List<String> teacherFullNames = new ArrayList<>();
        List<Student> students = studentsDao.findAll();
        List<Teacher> teachers = teachersDao.findAll();
        for (Student student : students)
            studentFullNames.add(student.getFirstName() + " " + student.getLastName());
        for (Teacher teacher : teachers)
            teacherFullNames.add(teacher.getFirstName() + " " + teacher.getLastName());

        req.setAttribute("studentFullNames", studentFullNames);
        req.setAttribute("teacherFullNames", teacherFullNames);
        req.getServletContext().getRequestDispatcher("/jsp/adminsPart/datedLessonsAdd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Сделать серверную валидацию даты
        //Разделение полного имени на составляющие
        String[] studentFirsAndLastName = req.getParameter("studentFullName").split(" ");
        String[] teacherFirsAndLastName = req.getParameter("teacherFullName").split(" ");

        //Парсинг datetime-local в Timestamp
        String datetimeLocal = req.getParameter("datedLessonDate");
        datetimeLocal = datetimeLocal + ":00.00";
        Timestamp timestamp = Timestamp.valueOf(datetimeLocal.replace("T"," "));

        //Сохранение назначенного урока, проверив, есть есть ли вписанный ученик и преподаватель в БД
        Optional<Student> studentOptional = studentsDao.findByFullName(studentFirsAndLastName[0], studentFirsAndLastName[1]);
        Optional<Teacher> teacherOptional = teachersDao.findByFullName(teacherFirsAndLastName[0], teacherFirsAndLastName[1]);
        if (studentOptional.isPresent() && teacherOptional.isPresent()) {
            DatedLesson datedLesson = new DatedLesson(
                    timestamp,
                    studentOptional.get(),
                    teacherOptional.get());
            datedLessonsDao.save(datedLesson);
        }
        resp.sendRedirect(req.getContextPath() + "/adminsPart/datedLessons/add");
    }

}
