package tools.gnzlz.javascript.sequelize.models;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.*;

public class ModelBase {

    private static String sequelize = "Sequelize";
    private static String dateTypes = "DataTypes";

    public static void create(ACTable table, ACCatalog catalog, String path){
        FileCreator.createFile(path ,table.name,"js", File.New()
            .Text("import { " , dateTypes , " } from '" , sequelize.toLowerCase() , "';")
            .ForFunction(ACRelation.class, table.hasOneNameImports(), (content, relation, index) -> {
                content.Line("import " , relation.pkColumn.table.nameCamelCase() , " from '../../model/" , relation.pkColumn.table.name , ".js';");
            })
            .Line("import " , table.nameCamelCase() , "Validation from '../../validation/model/" , table.name , ".js';").Line(1)
            .Line("import ",  catalog.nameCamelCase() , "Database from '../../../database.js';").Line(1)
            .Template(Block.New("const ", table.nameCamelCase(), " = ").Block(Content.New()
                .Template(Block.New("attributes : ").Block(Content.New()
                    .ForFunction(ACColumn.class, table.columns, (content, column, index) -> {
                        if(index != 0) content.Text(",");
                        content.Template(Block.New(column.name, ": ").Block(Content.New()
                            .Line("type: ", dateTypes, ".", typeValue(column.type), ",")
                            .Line("allowNull: ", column.nullable ? "true" : "false", ",")
                            .Line("validate: ", table.nameCamelCase(), "Validation.", column.name, ",")
                            .Line(!column.def.isEmpty(), "defaultValue: ", typeDefault(column.type, column.def), ",")
                            .Line(column.autoincrement, "autoIncrement: true,")
                            .Line(column.isPrimaryKey(), "primaryKey: true,")
                            .ForFunction(ACRelation.class, column.hasOne(), (contentRelation, relation, indexRelation)->{
                                contentRelation.Template(Block.New("references: ").Block(Content.New()
                                    .Line("model: ", relation.pkColumn.table.nameCamelCase(), ",")
                                    .Line("key: '", relation.pkColumn.name ,"'")
                                ));
                            })
                        ));
                    })
                )).Text(",")
                .Template(Block.New("options : ").Block(Content.New()
                    .Line(sequelize.toLowerCase(), ": ", catalog.nameCamelCase() , "Database,")
                    .Line("modelName: '" , table.nameCamelCase() , "',")
                    .Line("tableName: '" , table.name , "',")
                    .Line("timestamps: false,")
                    .Line("createdAt: false,")
                    .Line("updatedAt: false")
                ))
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default ", table.nameCamelCase(), ";")
        .content(), true);
    }

    public static String typeValue(String type){
        String split[] = type.split(" ");
        type = split == null ? type : split[0];
        String args = split != null && split.length > 1 && !split[1].equals("()")? split[1]: "";

        if(args.equals("UNSIGNED")){
            args = "";
        }

        if(type.equalsIgnoreCase("INT"))
            return "INTEGER" + args;
        if(type.equalsIgnoreCase("BIT"))
            return "BOOLEAN" + args;
        if(type.equalsIgnoreCase("SMALLINTEGER"))
            return "SMALLINT"+ args;
        if(type.equalsIgnoreCase("BIGINTEGER") || type.equalsIgnoreCase("LONG"))
            return "BIGINT" + args;
        if(type.equalsIgnoreCase("NUMERIC"))
            return "NUMBER" + args;
        if(type.equalsIgnoreCase("VARBINARY") || type.equalsIgnoreCase("BINARY"))
            return "STRING.BINARY" + args;
        if(type.equalsIgnoreCase("DATETIME"))
            return "DATE" + args;
        if(type.equalsIgnoreCase("DATE") || type.equalsIgnoreCase("TIMESTAMP"))
            return "DATEONLY" + args;
        if(type.equalsIgnoreCase("TIMESTAMP"))
            return "DATETIME" + args;
        if(type.equalsIgnoreCase("VARCHAR") || type.equalsIgnoreCase("LONGVARCHAR"))
            return "STRING" + args;
        if(type.equalsIgnoreCase("LONGVARCHAR") || type.equalsIgnoreCase("LONGTEXT") || type.equalsIgnoreCase("MEDIUMTEXT"))
            return "TEXT" + args;
        if(type.equalsIgnoreCase("CLOB"))
            return "BLOB" + args;

        return type + args;
    }

    public static String typeDefault(String type, String def){
        String split[] = type.split(" ");
        type = split == null ? type : split[0];

        if(type.equalsIgnoreCase("DATETIME") || type.equalsIgnoreCase("DATE") || type.equalsIgnoreCase("TIMESTAMP") ||
            type.equalsIgnoreCase("TIMESTAMP") || type.equalsIgnoreCase("VARCHAR") || type.equalsIgnoreCase("LONGVARCHAR") ||
            type.equalsIgnoreCase("LONGVARCHAR") || type.equalsIgnoreCase("CLOB"))
            return "'" + def + "'";

        return def;
    }
}
