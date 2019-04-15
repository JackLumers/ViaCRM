package ru.jacklumers.servlets.schoolworkConstructor;

import ru.jacklumers.dao.schoolworkConstructorDao.SpecializationDao;
import ru.jacklumers.dao.schoolworkConstructorDao.SpecializationDaoJdbcTemplateImpl;
import ru.jacklumers.models.schoolworkConstructor.Specialization;
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

@WebServlet("/schoolworkConstructor/specializations")
public class SpecializationsServlet extends HttpServlet {

    private SpecializationDao specializationDao;

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties sysProperties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(sysProperties);
        specializationDao = new SpecializationDaoJdbcTemplateImpl(dataSource);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Specialization> specializations = specializationDao.findAll();
        req.setAttribute("specializationsFromServer", specializations);
        req.getServletContext()
                .getRequestDispatcher("/jsp/schoolworkConstructor/specializations.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String specToSaveName = req.getParameter("specializationNameToSave");
        String specToDeleteIdAsString = req.getParameter("specializationIdToDelete");

        if(specToSaveName != null && !specToSaveName.isEmpty()){
            //Сохранение нового направления
            saveSpecialization(specToSaveName);
        }
        if (specToDeleteIdAsString != null && !specToDeleteIdAsString.isEmpty()){
            //Удаление направления
            deleteSpecialization(Long.parseLong(specToDeleteIdAsString));
        }

        resp.sendRedirect(req.getContextPath() + "/schoolworkConstructor/specializations");
    }

    private void saveSpecialization(String specName){
        specializationDao.save(new Specialization(specName));
    }

    private void deleteSpecialization(Long specId){
        specializationDao.delete(specId);
    }
}
