package tools.gnzlz.javascript.express.routes;

import file.FileCreator;
import template.File;

public class RouteSequelizeLogin {

    public static void create(String path, String absolute){
        FileCreator.createFile(path , "login", "js", File.New()
            .Text("import {authenticate} from '../controller/login.js';").Line(1)
            .Line("import express from 'express';").Line(1)
            .Line("const Login = express();").Line(1)
            .Line("/*******************************************")
            .Line(" * Routes ")
            .Line(" *******************************************/").Line(1)
            .Line("Login.post('", absolute, "/login', authenticate);").Line(1)
            .Line("/*******************************************")
            .Line(" * Routes ")
            .Line(" *******************************************/").Line(1)
            .Line("export default Login;")
        .content(), false);
    }
}
