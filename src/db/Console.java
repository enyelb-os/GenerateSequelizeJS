package db;

import tools.gnzlz.javascript.express.controllers.ControllerBase;
import tools.gnzlz.javascript.express.controllers.Controller;
import tools.gnzlz.javascript.express.controllers.ControllerValidation;
import tools.gnzlz.javascript.express.controllers.LoginController;
import tools.gnzlz.javascript.middlewares.JWTBase;
import tools.gnzlz.javascript.express.routes.ConfigRoute;
import tools.gnzlz.javascript.express.routes.LoginRoute;
import tools.gnzlz.javascript.express.routes.RouteBase;
import tools.gnzlz.javascript.express.routes.Route;
import tools.gnzlz.javascript.sequelize.models.ConfigSequelize;
import tools.gnzlz.javascript.sequelize.models.ModelBase;
import tools.gnzlz.javascript.sequelize.models.Model;
import tools.gnzlz.javascript.sequelize.models.ModelValidation;
import tools.gnzlz.javascript.sequelize.repositories.ConfigRepository;
import tools.gnzlz.javascript.sequelize.repositories.RepositoryBase;
import tools.gnzlz.javascript.sequelize.repositories.Repository;
import tools.gnzlz.javascript.sequelize.repositories.RepositoryValidation;
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
