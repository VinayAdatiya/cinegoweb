package config;

import common.Message;
import common.exception.ApplicationException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.SQLException;

@WebServlet(name = "DatabaseInitializer" , value = "/dbvalidator" , description = "Database Validator", loadOnStartup = 1)
public class DatabaseInitializer extends HttpServlet {
    @Override
    public void init() {
        ServletContext context = getServletConfig().getServletContext();
        try {
            String url = context.getInitParameter("db.url");
            String username = context.getInitParameter("db.username");
            String password = context.getInitParameter("db.password");
            String driver = context.getInitParameter("db.driver");
            if (url == null || username == null || password == null || driver == null) {
                throw new ApplicationException(Message.Error.DB_CONNECTION_FAILED);
            }
            DBConnection.getInstance(url, username, password, driver).validateConnection();
            System.out.println(Message.Success.DB_CONNECTION_SUCCESS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
