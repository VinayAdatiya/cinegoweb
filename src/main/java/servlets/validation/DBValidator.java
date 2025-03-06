package servlets.validation;

import common.Message;
import common.exception.ApplicationException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBValidator extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletConfig().getServletContext();
        Connection connection;
        try {
            String url = context.getInitParameter("db.url");
            String username = context.getInitParameter("db.username");
            String password = context.getInitParameter("db.password");
            String driver = context.getInitParameter("db.driver");
            if(url == null || username == null || password == null || driver == null){
                throw new ApplicationException(Message.Error.DB_CONNECTION_FAILED);
            }
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
            System.out.println(Message.Success.DB_CONNECTION_SUCCESS);
            DBConnection.setDBConfig(url,username,password,driver);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ApplicationException(Message.Error.DB_CONNECTION_FAILED);
        }
    }
}
