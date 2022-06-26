package tools.gnzlz.javascript.express.controllers;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACTable;

public class ControllerBase {

    public static void create(ACTable table, String path){
        FileCreator.createFile(path ,table.name,"js", File.New()
            .Text("import ", table.nameCamelCase(), "Repository from '../repository/", table.name , ".js';")
            .Line("import ", table.nameCamelCase(), "Validation from '../../validation/controller/", table.name , ".js';").Line(1)
            .Line("/*******************************************")
            .Line(" * Find ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const find = async (req, res) => ").Block(Content.New()
                .Template(Block.New("try ").Block(Content.New()
                    .Line("const data = await ", table.nameCamelCase(), "Repository.find(req.params.size, req.params.limit, req.params.column, req.params.value, req.params);")
                    .Template(Block.New("res.status(200).json(").Block(Content.New()
                        .Line("results: data.rows,")
                        .Line("total: data.count,")
                        .Line("size: data.size,")
                        .Line("page: data.page")
                    )).Text(");")
                )).Template(Block.New(" catch (error) ").Block(Content.New()
                    .Line("res.status(500).json(error.errors);")
                ))
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Create ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const create = async (req, res) => ").Block(Content.New()
                .Template(Block.New("try ").Block(Content.New()
                    .Line("const data = await ", table.nameCamelCase(), "Repository.create(req.body, req.params);")
                    .Template(Block.New("res.status(200).json(").Block(Content.New()
                        .Line("results: data,")
                        .Line("total: data.length")
                    )).Text(");")
                )).Template(Block.New(" catch (error) ").Block(Content.New()
                    .Line("res.status(500).json(error.errors);")
                ))
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Funtions ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const ", table.nameCamelCase(), " = ").Block(Content.New()
                .Line("find,")
                .Line("create,")
                .Line("... ", table.nameCamelCase(), "Validation")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), true);
    }
}
