package tools.gnzlz.javascript.sequelize.models;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACCatalog;
import tools.gnzlz.database.model.interfaces.Dialect;
import tools.gnzlz.database.properties.PTConnection;

public class ConfigSequelize {

    private static String sequelize = "Sequelize";
    private static String dateTypes = "DataTypes";

    public static void create(PTConnection props, ACCatalog catalog, String path){
        FileCreator.createFile(path , "database","js", File.New()
            .Text("import { " , sequelize , " } from '" , sequelize.toLowerCase() , "';").Line(1)
            .Template(Block.New("const ", catalog.nameCamelCase() ," = new Sequelize('", catalog.name, "', '", props.user(),"', '", props.password(), "', ").Block(Content.New()
                .Line("host: '", props.host(),"',")
                .Line("port: '", props.port(),"',")
                .Line("dialect: '", dialect(props.dialect()) ,"',")
            )).Text(");")
            .Line()
            .Line("export default ",catalog.nameCamelCase(),";")
        .content(), false);
    }

    private static String dialect(Dialect dialect){
        return
            dialect == Dialect.MySQL ? "mysql" :
            dialect == Dialect.SQLite ? "sqlite" :
            dialect == Dialect.PostgreSQL ? "postgres" : "mysql";
    }
}
