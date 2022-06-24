package sequelize.repositories;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACTable;

public class Repository {

    public static void create(ACTable table, String path){
        FileCreator.createFile(path , table.name, "js", File.New()
            .Line("import ", table.nameCamelCase(), "Repository from '../base/repository/" , table.name , ".js';").Line(1)
            .Line("/*******************************************")
            .Line(" * Funtions ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const ", table.nameCamelCase(), " = ").Block(Content.New()
                .Line("... ", table.nameCamelCase(), "Repository")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), false);
    }
}
