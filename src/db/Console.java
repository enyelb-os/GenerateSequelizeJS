package db;

import controllers.ControllerBase;
import controllers.Controller;
import routes.ConfigRoute;
import routes.RouteBase;
import routes.Route;
import sequelize.models.ConfigSequelize;
import sequelize.models.ModelBase;
import sequelize.models.Model;
import sequelize.models.ModelValidation;
import sequelize.repositories.ConfigRepository;
import sequelize.repositories.RepositoryBase;
import sequelize.repositories.Repository;
import sequelize.repositories.RepositoryValidation;
import tools.gnzlz.database.autocode.model.ACDataBase;
import tools.gnzlz.database.autocode.model.ACTable;
import tools.gnzlz.database.model.DBConfiguration;

public class Console {

    public static String host = "";
    public static int port = -1;
    public static String user = "";
    public static String password = "";
    public static String name = "";
    public static String type = "mysql";

    public static void main(String[] args) {
        String option = "";
        for (String code: args) {
            if(code.substring(0,1).equals("-")){
                option = code;
            } else {
                switch (option){
                    case "--host": case "-h":
                        host = code; break;
                    case "--port":
                        port = Integer.parseInt(code); break;
                    case "--user": case "-u":
                        user = code; break;
                    case "--pass": case "-p":
                        password = code; break;
                    case "--name": case "-n":
                        name = code; break;
                    case "--type": case "-t":
                        type = code; break;
                }
            }
        }

        if(type.equalsIgnoreCase("sqltite")){

        } else {
            if(!host.isEmpty()) MySQL.host = host;
            if(port != -1) MySQL.port = port;
            if(!user.isEmpty()) MySQL.user = user;
            if(!password.isEmpty()) MySQL.password = password;
            if(!name.isEmpty()) MySQL.name = name;
            generate(MySQL.class,name);
        }
    }

    public static <T extends DBConfiguration> void generate(Class<T> c, String name) {
        ACDataBase dataBase = ACDataBase.dataBase(c, name);
        dataBase.catalogs.forEach(catalog -> {
            ConfigSequelize.create(dataBase.configuration.connection().properties(), catalog, catalog.name);
            ConfigRepository.create(catalog.name);
            catalog.schemes.forEach(scheme -> {
                String schemeName = scheme.nameDefault();
                ConfigRoute.create(scheme, catalog.name);
                for (ACTable table: scheme.tables){
                    ModelBase.create(table, catalog, catalog.name + "/" + schemeName + "/base/model");
                    Model.create(table, catalog.name + "/" + schemeName + "/model");
                    ModelValidation.create(table, catalog.name + "/" + schemeName + "/validation/model");
                    RouteBase.create(table, catalog.name + "/" + schemeName + "/base/route" );
                    Route.create(table, catalog.name + "/" + schemeName + "/route");
                    ControllerBase.create(table, catalog.name + "/" + schemeName + "/base/controller");
                    Controller.create(table, catalog.name + "/" + schemeName + "/controller");
                    RepositoryBase.create(table, catalog.name + "/" + schemeName + "/base/repository");
                    Repository.create(table, catalog.name + "/" + schemeName + "/repository");
                    RepositoryValidation.create(table, catalog.name + "/" + schemeName + "/validation/repository");
                }
            });
        });
    }
}
