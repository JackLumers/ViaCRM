package ru.jacklumers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Проверка аутентификации пользователя (был ли вход до этого)
 * <p>
 * Фильтры принимают на себя все запросы, до их обработки сервлетом.
 * <p>
 * Он либо отдает управление другому фильтру или сервлету,
 * либо выбрасывает ошибку.
 */
@WebFilter("/home")
public class AuthFilter implements Filter {

    /**
     * Инициализация фильтра
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Фильтрация запроса
     *
     * @param chain - цепочка фильтров, обрабатывающая данный запрос
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //Взятие сессии по запросу, без её создания, если её нет.
        HttpSession session = request.getSession(false);

        /* Проверяем сессию клиента.
        Если она не сущетвует и её аттрибут "userName" не существует,
        перенаправляем запрос на сервлет signIn
         */
        if (session == null || session.getAttribute("userName") == null) {
            //Перенаправление запроса на сервлет signIn
            servletRequest.getServletContext().getRequestDispatcher("/signIn").forward(request, response);
        }

        /* Передает запрос следующему фильтру в цепочке, а если такого нет,
         * то ресурсу после цепочки фильтров, который обрабатывает данный запрос (сервлету) */
        chain.doFilter(request, response);
    }

    /**
     * Уничтожение фильтра
     */
    @Override
    public void destroy() {

    }
}
