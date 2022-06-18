package sequelize.repositories;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACCatalog;
import tools.gnzlz.database.autocode.model.ACColumn;
import tools.gnzlz.database.autocode.model.ACRelation;
import tools.gnzlz.database.autocode.model.ACTable;

public class RepositoryBase {

    private static String sequelize = "Sequelize";
    private static String dateTypes = "DataTypes";

    public static void create(ACTable table, String path){
        FileCreator.createFile(path ,table.name,"js", File.New()
            .ForFunction(ACRelation.class, table.hasOneNameImports(), (content, relation, index) -> {
                content.Line("import " , relation.pkColumn.table.nameCamelCase() , "Repository from './" , relation.pkColumn.table.name , ".js';");
            })
            .Line("import ", table.nameCamelCase(), "Model from '../../model/" , table.name, ".js';")
            .Line("import " , table.nameCamelCase() , "Validation from '../../validation/repository/" , table.name , ".js';")
            .Line("import BaseRepository from '../../../repository.js';").Line(1)
            .Line("/*******************************************")
            .Line(" * columns ")
            .Line(" *******************************************/").Line(1)
            .Line("const columns = [")
            .ForFunction(ACColumn.class, table.columns, (content, column, index) -> {
                if(index != 0) content.Text(", ");
                content.Text("'", column.name, "'");
            }).Text("];").Line(1)
            .Line("/*******************************************")
            .Line(" * Find Properties  ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const findProperties = (size, limit, column, value, params) => ").Block(Content.New()
                .Line("return BaseRepository.columnWhereLimit(")
                .Template(Content.New()
                    .Text("\t\t",table.nameCamelCase(), "Validation.findProperties({}, params, columns), column, value, size, limit, columns")
                ).Line(");")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Exists Properties  ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const existsProperties = (data, params) => ").Block(Content.New()
                .Line("return ", table.nameCamelCase(), "Validation.existsProperties({}, data, params, columns);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Exists  ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const exists = async(data, params) =>").Block(Content.New()
                .Line("let properties = existsProperties(data, params);")
                .Template(Block.New("if(Object.keys(properties).length !== 0)").Block(Content.New()
                    .Line("return await ",table.nameCamelCase(), "Model.findOne(properties);")
                )).Line("return ",table.nameCamelCase(), "Model.build();")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * List ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const find = async(size, limit, column, value, params) =>").Block(Content.New()
                .Line("return BaseRepository.pageLimit(").Template(Content.New()
                    .Text("\t\tawait ", table.nameCamelCase(), "Model.findAndCountAll(findProperties(size, limit, column, value, params))")
                ).Line(", size, limit);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * CreateList ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const create = async(values, params) => ").Block(Content.New()
                .Line("let results = [];")
                .Template(Block.New("for(let data of BaseRepository.parseArray(values))").Block(Content.New()
                    .ForFunction(ACColumn.class, table.columns, (content, column, index) -> {
                        content.ForFunction(ACRelation.class, column.hasOne(), (c, r, i) -> {
                            c.Line("data.", r.fkColumn.name, " = await BaseRepository.searchForeignKey(").Template(Content.New()
                                .Text("\t\t\tdata.",r.fkColumn.name, ", ", r.pkColumn.table.nameCamelCase(), "Repository, '", r.pkColumn.name, "'")
                            ).Line(");");
                        });
                    }).Line(!table.hasOneNameImports().isEmpty(), "")
                    .Line("results.push(").Template(Content.New()
                        .Text("\t\t\tawait BaseRepository.createOrUpdate(").Template(Content.New()
                            .Text("\t\t\t\tawait exists(data, params), data, columns")
                        ).Line(")")
                    ).Line(");")
                )).Line("return BaseRepository.dataResponse(results);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Funtions ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const ", table.nameCamelCase(), " = ").Block(Content.New()
                .Line("find,")
                .Line("create")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), true);
    }
}
