package Utility.DbUtility;

import java.sql.*;

public class DatabaseUtility {

    private Connection connection;
    private static final  String URL=null;
    private static final  String USER=null;
    private static final String PASSWORD=null;

    public DatabaseUtility()
    {
        try {
            connection= DriverManager.getConnection(URL,USER,PASSWORD);

        }catch (SQLException e)
        {
            throw new RuntimeException("Database connection failed",e);
        }
    }

    public ResultSet executeQuery(String query)
    {
        try {

            Statement stmt=connection.createStatement();
            return  stmt.executeQuery(query);
        }

        catch (SQLException e)
        {
            throw  new RuntimeException("Query execution failed",e);
        }
    }

    public int executeUpdate(String query)
    {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Update execution failed!", e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection!", e);
        }
    }



}
