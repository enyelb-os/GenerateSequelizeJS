package db;

import controllers.ControllerBase;
import controllers.Controller;
import controllers.ControllerValidation;
import controllers.LoginController;
import middlewares.JWTBase;
import routes.ConfigRoute;
import routes.LoginRoute;
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



    public static void main(String[] args) {

        Command command = Command.parse(args);

        if(command.type.equalsIgnoreCase("sqltite")){

        } else {
            MySQL.initConfig(command);
            generate(MySQL.class, command);
        }
    }

    public static <T extends DBConfiguration> void generate(Class<T> c, Command command) {
        ACDataBase dataBase = ACDataBase.dataBase(c, command.name);
        dataBase.catalogs.forEach(catalog -> {
            ConfigSequelize.create(dataBase.configuration.connection().properties(), catalog, catalog.name);
            ConfigRepository.create(catalog.name);
            if(command.jwt && command.express){
                JWTBase.create(catalog.name + "/middleware");
            }
            catalog.schemes.forEach(scheme -> {
                String schemeName = scheme.nameDefault();
                String absolute = "";
                if(command.modules){
                    absolute = "/" + catalog.name + "/" + schemeName;
                }
                if(command.express){
                    ConfigRoute.create(scheme, catalog.name, command);
                    if(command.jwt){
                        LoginRoute.create(catalog.name + "/" + schemeName + "/route", absolute);
                        LoginController.create(catalog.name + "/" + schemeName + "/controller");
                    }
                }
                for (ACTable table: scheme.tables){
                    ModelBase.create(table, catalog, catalog.name + "/" + schemeName + "/base/model");
                    Model.create(table, catalog.name + "/" + schemeName + "/model");
                    ModelValidation.create(table, catalog.name + "/" + schemeName + "/validation/model");
                    RepositoryBase.create(table, catalog.name + "/" + schemeName + "/base/repository");
                    Repository.create(table, catalog.name + "/" + schemeName + "/repository");
                    RepositoryValidation.create(table, catalog.name + "/" + schemeName + "/validation/repository");
                    if(command.express){
                        RouteBase.create(table, catalog.name + "/" + schemeName + "/base/route", absolute);
                        Route.create(table, catalog.name + "/" + schemeName + "/route");
                        ControllerBase.create(table, catalog.name + "/" + schemeName + "/base/controller");
                        Controller.create(table, catalog.name + "/" + schemeName + "/controller");
                        ControllerValidation.create(table, catalog.name + "/" + schemeName + "/validation/controller", command);
                    }
                }
            });
        });
    }
}
