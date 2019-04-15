package ru.jacklumers.servlets.schoolworkConstructor;

import ru.jacklumers.dao.schoolworkConstructorDao.LearningStageDao;
import ru.jacklumers.dao.schoolworkConstructorDao.LearningStageDaoJdbcTemplateImpl;
import ru.jacklumers.dao.schoolworkConstructorDao.SpecializationDao;
import ru.jacklumers.dao.schoolworkConstructorDao.SpecializationDaoJdbcTemplateImpl;
import ru.jacklumers.models.schoolworkConstructor.LearningStage;
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
import java.util.Optional;
import java.util.Properties;

@WebServlet("/schoolworkConstructor/learningStages")
public class LearningStagesServlet extends HttpServlet {

    private LearningStageDao learningStageDao;
    private SpecializationDao specializationDao;

    @Override
    public void init() throws ServletException {
        //Загрузка файла настроек
        Properties sysProperties = PropertiesLoader.loadProperties("sys.properties", getServletContext().getRealPath("/WEB-INF/classes"));
        //Получение DataSource по данной конфигурации
        DataSource dataSource = DataSourceBuilder.buildDataSourceUsingProperties(sysProperties);
        learningStageDao = new LearningStageDaoJdbcTemplateImpl(dataSource);
        specializationDao = new SpecializationDaoJdbcTemplateImpl(dataSource);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<LearningStage> learningStages = learningStageDao.findAll();
        List<Specialization> specializations = specializationDao.findAll();

        req.setAttribute("learningStagesFromServer", learningStages);
        req.setAttribute("specializationsFromServer", specializations);

        req.getServletContext()
                .getRequestDispatcher("/jsp/schoolworkConstructor/learningStages.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stageNameToSave = req.getParameter("learningStageNameToSave");
        String stageSpecNameToSave = req.getParameter("learningStageSpecNameToSave");
        String stageIdToDeleteAsString = req.getParameter("learningStageIdToDelete");

        //Сохранение нового этапа обучения
        if (stageNameToSave != null && stageSpecNameToSave != null
                && !stageNameToSave.isEmpty() && !stageSpecNameToSave.isEmpty()) {
            saveLearningStage(stageNameToSave, stageSpecNameToSave);
        }
        //Удаление этапа
        if (stageIdToDeleteAsString != null && !stageIdToDeleteAsString.isEmpty()) {
            deleteLearningStage(stageIdToDeleteAsString);
        }

        resp.sendRedirect(req.getContextPath() + "/schoolworkConstructor/learningStages");
    }

    private void saveLearningStage(String stageName, String specName) {
        Optional<Specialization> optionalSpecialization = specializationDao.findSpecByName(specName);
        if (optionalSpecialization.isPresent()) {
            LearningStage learningStage = new LearningStage(stageName, optionalSpecialization.get());
            learningStageDao.save(learningStage);
        }
    }

    private void deleteLearningStage(String stageIdAsString) {
        Long stageId = Long.parseLong(stageIdAsString);
        learningStageDao.delete(stageId);
    }
}
