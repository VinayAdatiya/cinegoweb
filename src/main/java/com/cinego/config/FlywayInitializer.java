package com.cinego.config;

import com.cinego.common.Message;
import org.flywaydb.core.Flyway;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class FlywayInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        String url = context.getInitParameter("db.url");
        String user = context.getInitParameter("db.username");
        String password = context.getInitParameter("db.password");
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .baselineVersion("1")
                    .load();
            flyway.baseline();
            flyway.migrate();
            System.out.println(Message.Success.FLYWAY_SUCCESS);
        } catch (Exception e) {
            System.err.println(Message.Error.FLYWAY_FAILED);
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}