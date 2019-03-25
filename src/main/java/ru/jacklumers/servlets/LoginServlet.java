package ru.jacklumers.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO: Добавить вход в качестве препода/админа
 * Сервлет для обработки входа на сайт
 */
@WebServlet("/signIn")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String userName = req.getParameter("userName");
//        String password = req.getParameter("password");
//
//        if (usersRepository.isExist(userName, password)) {
//            HttpSession session = req.getSession();
//            /* Сохранение атрибута userName в сессии, чтобы позже
//             * пускать клиент с данной сессией, как уже авторизовавшегося
//             */
//            session.setAttribute("userName", userName);
//            resp.sendRedirect(req.getContextPath() + "/home");
//        } else {
//            resp.sendRedirect(req.getContextPath() + "/signIn");
//        }
    }
}
