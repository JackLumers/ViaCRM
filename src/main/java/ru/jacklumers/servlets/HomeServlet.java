package ru.jacklumers.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Домашняя страница
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    /**
     * Обработка GET-запроса HomeServlet'ом
     * В отличии от POST запроса, данные для GET могут быть передан в строке запроса URL.
     *
     * @param req  - запрос
     * @param resp - ответ
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /* RequestDispatcher перенаправляет запрос на другой ресурс.
         * Конкретно здесь запрос перенаправляется на jsp */
        req.getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
    }

    /**
     * Реализация POST запроса. Данные передаются на сервер в теле сообщения.
     * Будучи закрытыми в теле, их не видно в строке запроса URL.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем параметр запроса
        String color = req.getParameter("color");
        // Создаем Cookie с данным значением
        Cookie colorCookie = new Cookie("color", color);
        // Кладем Cookie в ответ
        resp.addCookie(colorCookie);
        // Перенаправляем пользователя обратно на эту же страницу
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
