import java.sql.*;
import java.util.Properties;

public class AppsIntroduction {
    private static Connection connection;
    private static String query;
    private static PreparedStatement statement;
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";

    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","sqlsafe500");

        connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME,properties);

        query = "SELECT name FROM minions";
        statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %n",resultSet.getString("name"));

        }

    }
}
