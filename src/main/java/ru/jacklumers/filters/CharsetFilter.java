package ru.jacklumers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Проверка кодировки запросов.
 * Если не в нужной кодировке, то осуществляем перевод в нее.
 * Кодировка записана в config.properties -> requests.encoding
 */
@WebFilter("/*")
public class CharsetFilter implements Filter {

    private Properties properties = new Properties();
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            properties.load(new FileInputStream(filterConfig.getServletContext().getRealPath("/WEB-INF/classes/config.properties")));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        encoding = properties.getProperty("requests.encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
