package tools.gnzlz.javascript.express.controllers;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;

public class LoginController {

    public static void create(String path){
        FileCreator.createFile(path, "login", "js", File.New()
            .Line("import { createToken, validateToken } from '../../middleware/jwt.js';").Line(1)
            .Line("/*******************************************")
            .Line(" * Authenticate ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const authenticate = async (req, res) => ").Block(Content.New()
                .Template(Block.New("try ").Block(Content.New()
                    .Line("const { user, password } = req.body;").Line(1)
                    .Template(Block.New("const token = await createToken(").Block(Content.New()
                        .Line("user, password")
                    )).Text(", 'secret seed', '5d');").Line(1)
                    .Template(Block.New("res.status(200).json(").Block(Content.New()
                        .Line("ok: true,")
                        .Line("code: 200,")
                        .Line("msg: 'new token',")
                        .Line("token")
                    )).Text(");")
                )).Template(Block.New(" catch (error) ").Block(Content.New()
                    .Line("res.status(401).json({ok: false, code: 401, msg: error.message });")
                ))
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Validate Authenticate ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const validateAuthenticate = async (req, res) => ").Block(Content.New()
                .Line("const token = req.header('x-token');")
                .Line("const response = validateToken(token, 'secret seed');")
                .Template(Block.New("if(!response.ok) ").Block(Content.New()
                    .Line("res.status(401).json(response);")
                ))
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("export ").Block(Content.New()
                .Line("authenticate,")
                .Line("validateAuthenticate")
            )).Text(";")
        .content(), false);
    }
}
