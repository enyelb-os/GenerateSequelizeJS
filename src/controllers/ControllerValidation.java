package controllers;

import db.Command;
import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACTable;

public class ControllerValidation {

    public static void create(ACTable table, String path, Command command){
        FileCreator.createFile(path,table.name,"js",File.New()
            .Text(command.express && command.jwt,"import { validateAuthenticate } from '../../controller/login.js';").Line(1)
            .Line("/*******************************************")
            .Line(" * Before Find")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const beforeFind = async (req, res, next) => ").Block(Content.New()
                .Line(command.express && command.jwt,"validateAuthenticate(req, res);")
                .Line("next();")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Before Create ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const beforeCreate = async (req, res, next) => ").Block(Content.New()
                .Line(command.express && command.jwt,"validateAuthenticate(req, res);")
                .Line("next();")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Funtions ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const ", table.nameCamelCase(), " = ").Block(Content.New()
                .Line("beforeFind,")
                .Line("beforeCreate")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), false);
    }
}
