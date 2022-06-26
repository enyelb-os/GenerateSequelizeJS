package tools.gnzlz.javascript.express.routes;

import file.FileCreator;
import template.File;
import tools.gnzlz.database.autocode.model.ACTable;

public class Route {

    public static void create(ACTable table, String path){
        FileCreator.createFile(path , table.name, "js", File.New()
            .Text("import " , table.nameCamelCase(), " from '../base/route/" , table.name , ".js';").Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), false);
    }
}
