package routes;

import file.FileCreator;
import template.File;
import tools.gnzlz.database.autocode.model.*;

public class ConfigRoute {

    public static void create(ACScheme scheme, String path){
        FileCreator.createFile(path , scheme.nameDefault() , "js", File.New()
            .Text("import express from 'express';").Line(1)
            .ForFunction(ACTable.class, scheme.tables, (content, table, index) -> {
                content.Line("import ", table.nameCamelCase(), " from './", scheme.nameDefault(), "/route/",table.name,".js';");
            })
            .Line("const ruters = express();").Line(1)
            .Line("/*******************************************")
            .Line(" * ruters ")
            .Line(" *******************************************/").Line(1)
            .ForFunction(ACTable.class, scheme.tables, (content, table, index) -> {
                content.Line("ruters.use('/',", table.nameCamelCase(), ");");
            }).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ruters;")
        .content(), true);
    }
}
