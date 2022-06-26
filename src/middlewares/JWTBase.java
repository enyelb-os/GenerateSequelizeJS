package middlewares;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;

public class JWTBase {

    public static void create(String path){
        FileCreator.createFile(path ,"jwt","js", File.New()
            .Line("import jwt from 'jsonwebtoken';").Line(1)
            .Line("/*******************************************")
            .Line(" * Create Token ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const createToken = (payload, secret_seed, time) => ").Block(Content.New()
                .Template(Block.New("return new Promise((resolve, reject) => ").Block(Content.New()
                    .Template(Block.New("jwt.sign(payload, secret_seed, {expiresIn: time}, (err, token) => ").Block(Content.New()
                        .Template(Block.New("if (err) ").Block(Content.New()
                            .Line("reject('error creating token');")
                        ))
                        .Line("resolve(token);")
                    )).Text(");")
                )).Text(");")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Validate Token ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const validateToken = (token, secret_seed) => ").Block(Content.New()
                .Line("if (!token) return { ok : false, msg : 'no token'};")
                .Template(Block.New("try ").Block(Content.New()
                    .Line("return {ok : true, ... jwt.verify(token, secret_seed)};")
                )).Template(Block.New(" catch (error) ").Block(Content.New()
                    .Line("return { ok : false, msg : 'invalid token'};")
                ))
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("export ").Block(Content.New()
                .Line("createToken,")
                .Line("validateToken")
            )).Text(";")
        .content(), true);
    }
}
