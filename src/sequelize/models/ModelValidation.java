package sequelize.models;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACColumn;
import tools.gnzlz.database.autocode.model.ACTable;

public class ModelValidation {

    private static String sequelize = "Sequelize";
    private static String model = "Model";

    public static void create(ACTable table, String path){
        FileCreator.createFile(path,table.name,"js",File.New()
            .Line("/*******************************************")
            .Line(" * Validation ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const ", table.nameCamelCase()," = ").Block(Content.New()
                .ForFunction(ACColumn.class, table.columns, (content, column, index) -> {
                    if(index != 0) content.Text(",");
                    content.Line(column.name, ": {}");
                })
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), false);
    }
}
