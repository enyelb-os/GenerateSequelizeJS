package db;

import tools.gnzlz.database.model.DBConfiguration;
import tools.gnzlz.database.properties.PropertiesConnection;
import tools.gnzlz.database.properties.PropertiesMigration;
import tools.gnzlz.database.properties.PropertiesModel;

public class MySQL extends DBConfiguration {

    public static String host = "localhost";
    public static int port = 3306;
    public static String user = "root";
    public static String password = "";
    public static String name = "";

    @Override
    protected void initConnection(PropertiesConnection connection) {
        connection
            .dialect(MySQL).protocol("jdbc:mysql:").host(host).port(port)
            .user(user).password(password).name(name)
            .property("useSSL","false").property("serverTimezone","UTC");
    }

    @Override
    protected void initMigration(PropertiesMigration migration) {

    }

    @Override
    protected void initModel(PropertiesModel model) {
        model.modelPackage("db.mysql.modelo").refresh(false);
    }
}
