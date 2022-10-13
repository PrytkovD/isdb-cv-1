package ruitis.isdbcv1.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ruitis.isdbcv1.dao.DishDAO;
import ruitis.isdbcv1.dao.OrderDAO;
import ruitis.isdbcv1.dao.WorkderDAO;
import ruitis.isdbcv1.util.SessionDataHolder;

import java.io.IOException;
import java.util.Properties;

@WebListener
public class ServletListener implements ServletContextListener, HttpSessionListener {
    private WorkderDAO workderDAO;
    private DishDAO dishDAO;
    private OrderDAO orderDAO;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();

        try {
            properties.load(ServletListener.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HikariConfig hikariConfig = new HikariConfig(properties);
        hikariConfig.setDriverClassName(org.postgresql.Driver.class.getName());
        hikariConfig.setMaximumPoolSize(16);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        workderDAO = new WorkderDAO(hikariDataSource);
        dishDAO = new DishDAO(hikariDataSource);
        orderDAO = new OrderDAO(hikariDataSource);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("workerDAO", workderDAO);
        se.getSession().setAttribute("dishDAO", dishDAO);
        se.getSession().setAttribute("orderDAO", orderDAO);
        se.getSession().setAttribute("dataHolder", new SessionDataHolder());
    }
}
