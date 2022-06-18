package sequelize.repositories;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACColumn;
import tools.gnzlz.database.autocode.model.ACTable;

public class RepositoryValidation {

    public static void create(ACTable table, String path){
        FileCreator.createFile(path,table.name,"js",File.New()
            .Line("import BaseRepository from '../../../repository.js';").Line(1)
            .Line("/*******************************************")
            .Line(" * Exists Properties")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const existsProperties = (properties, data, params, columns) => ").Block(Content.New()
                .Line(table.primaryKey() != null, "properties = BaseRepository.columnAndWhere(properties, '", table.primaryKey().name, "', data.", table.primaryKey().name, ", columns);")
                .Line("return properties;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Find Properties ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const findProperties = (properties, params, columns) => ").Block(Content.New()
                .Line("return properties;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Funtions ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const ", table.nameCamelCase(), " = ").Block(Content.New()
                .Line("findProperties,")
                .Line("existsProperties,")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), false);
    }
}
