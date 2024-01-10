package jm.task.core.jdbc;

// url   jdbc:postgresql://localhost:5432/your_database_name

public class ConnectionData {
    public static final String DRIVER = "org.postgresql.Driver";
//    public static final String USER = "pgTaskJDBC_1";
    public static final String USER = "postgres";
    public static final String PASSWORD = "pguser";
    public static final String dbName = "postgres";
//    public static final String dbName = "pgTaskJDBC_1";
    public static final String URL = "jdbc:postgresql://localhost:5432/" + dbName;
    public static final String userTable = "users";

}
