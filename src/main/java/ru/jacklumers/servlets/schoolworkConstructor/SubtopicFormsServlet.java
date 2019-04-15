package ru.jacklumers.servlets.schoolworkConstructor;

import ru.jacklumers.dao.schoolworkConstructorDao.SubtopicFormDao;
import ru.jacklumers.dao.schoolworkConstructorDao.SubtopicFormDaoJdbcTemplateImpl;
import ru.jacklumers.models.schoolworkConstructor.SubtopicForm;
import ru.jacklumers.models.schoolworkConstructor.modelsBuilders.SubtopicFormBuilder;
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
import java.util.Optional;
import java.util.Properties;

@WebServlet("/schoolworkConstructor/subtopicForms")
public class SubtopicFormsServlet extends HttpServlet {

    private SubtopicFormDao subtopicFormDao;

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties sysProperties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(sysProperties);
        subtopicFormDao = new SubtopicFormDaoJdbcTemplateImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SubtopicForm> subtopicForms = subtopicFormDao.findAll();
        req.setAttribute("subtopicFormsFromServer", subtopicForms);
        req.getServletContext()
                .getRequestDispatcher("/jsp/schoolworkConstructor/subtopicForms.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subtopicFormNameToSave = req.getParameter("subtopicFormNameToSave");
        String subtopicFormIdToDeleteAsString = req.getParameter("subtopicFormIdToDelete");

        //Сохранение нового вида подтем
        if(subtopicFormNameToSave != null && !subtopicFormNameToSave.isEmpty()){
            saveSubtopicForm(subtopicFormNameToSave);
        }
        //Удаление вида
        if(subtopicFormIdToDeleteAsString != null && !subtopicFormIdToDeleteAsString.isEmpty()){
            deleteSubtopicForm(subtopicFormIdToDeleteAsString);
        }

        resp.sendRedirect(req.getContextPath() + "/schoolworkConstructor/subtopicForms");
    }

    private void saveSubtopicForm(String formName) {
        SubtopicForm subtopicForm = new SubtopicFormBuilder()
                .setName(formName)
                .createSubtopicForm();
        subtopicFormDao.save(subtopicForm);
    }

    private void deleteSubtopicForm(String formIdAsString) {
        subtopicFormDao.delete(Long.parseLong(formIdAsString));
    }
}

