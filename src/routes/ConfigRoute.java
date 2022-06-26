package routes;

import db.Command;
import file.FileCreator;
import template.File;
import tools.gnzlz.database.autocode.model.*;

public class ConfigRoute {

    public static void create(ACScheme scheme, String path, Command command){
        FileCreator.createFile(path , scheme.nameDefault() , "js", File.New()
            .Text("import express from 'express';").Line(1)
            .Line(command.express && command.jwt, "import Login from './", scheme.nameDefault(), "/route/login.js';")
            .ForFunction(ACTable.class, scheme.tables, (content, table, index) -> {
                content.Line("import ", table.nameCamelCase(), " from './", scheme.nameDefault(), "/route/",table.name,".js';");
            })
            .Line("const ruters = express();").Line(1)
            .Line("/*******************************************")
            .Line(" * ruters ")
            .Line(" *******************************************/").Line(1)
            .Line(command.express && command.jwt, "ruters.use('/',Login);").Line(1)
            .ForFunction(ACTable.class, scheme.tables, (content, table, index) -> {
                content.Line("ruters.use('/',", table.nameCamelCase(), ");");
            })
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ruters;")
        .content(), true);
    }
}
