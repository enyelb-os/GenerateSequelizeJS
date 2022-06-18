package routes;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACCatalog;
import tools.gnzlz.database.autocode.model.ACTable;

public class RouteBase {

    public static void create(ACTable table, String path){
        FileCreator.createFile(path ,table.name,"js", File.New()
            .Line("import " , table.nameCamelCase(), "Controller from '../controller/", table.name , ".js';")
            .Line("import express from 'express';").Line(1)
            .Line("const ", table.nameCamelCase() ," = express();").Line(1)
            .Line("/*******************************************")
            .Line(" * Routes ")
            .Line(" *******************************************/").Line(1)
            .Line(table.nameCamelCase(), ".get('/", table.name, "/list', " , table.nameCamelCase(), "Controller.find);")
            .Line(table.nameCamelCase(), ".get('/", table.name, "/list/:size/:limit', " , table.nameCamelCase(), "Controller.find);")
            .Line(table.nameCamelCase(), ".get('/", table.name, "/list/by/:column/:value', " , table.nameCamelCase(), "Controller.find);")
            .Line(table.nameCamelCase(), ".get('/", table.name, "/list/:size/:limit/by/:column/:value/', " , table.nameCamelCase(), "Controller.find);").Line(1)
            .Line(table.nameCamelCase(), ".post('/", table.name, "/create', " , table.nameCamelCase(), "Controller.create);").Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), true);
    }
}
