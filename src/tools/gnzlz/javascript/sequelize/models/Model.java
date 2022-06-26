package tools.gnzlz.javascript.sequelize.models;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACTable;

public class Model {

    private static String sequelize = "Sequelize";
    private static String model = "Model";

    public static void create(ACTable table, String path){
        FileCreator.createFile(path ,table.name,"js",File.New()
            .Text("import { " , model , " } from '" , sequelize.toLowerCase() , "';")
            .Line("import ", table.nameCamelCase(), "Base from '../base/model/", table.name, ".js';" ).Line(1)
            .Template(Block.New("class " , table.nameCamelCase() , " extends ", model).Block(Content.New().Line(1)
                .Line("/*******************************************")
                .Line(" * Custom Methods ", table.nameCamelCase())
                .Line(" *******************************************/").Line(1)
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Init ")
            .Line(" *******************************************/").Line(1)
            .Line(table.nameCamelCase(), ".init(", table.nameCamelCase(), "Base.attributes, ", table.nameCamelCase(), "Base.options);").Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), false);
    }
}
