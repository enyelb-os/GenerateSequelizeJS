package sequelize.repositories;

import file.FileCreator;
import template.Block;
import template.Content;
import template.File;
import tools.gnzlz.database.autocode.model.ACTable;

public class ConfigRepository {

    private static String sequelize = "Sequelize";

    public static void create(String path){
        FileCreator.createFile(path , "repository", "js", File.New()
            .Text("import { Op } from '" , sequelize.toLowerCase() , "';").Line(1)
            .Line("/*******************************************")
            .Line(" * Parse")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const parseArray = (data) => ").Block(Content.New()
                .Line("return Array.isArray(data) ? data : [data]")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Validate Properties ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const validateProperties = (data) => ").Block(Content.New()
                .Line("return Object.keys(data).length !== 0;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Add Properties ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const addProperties = (data, column, value) => ").Block(Content.New()
                .Line("data = parseArray(data);")
                .Template(Block.New("for(let d of data) ").Block(Content.New()
                    .Line("d[column] = value;")
                ))
                .Line("return data;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Is Dictionary ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const isDictionary = (dict) => ").Block(Content.New()
                .Line("return typeof dict === 'object' && !Array.isArray(dict);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Validate Column ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const validateColumn = (column, columns) => ").Block(Content.New()
                .Template(Block.New("for(let c of columns) ").Block(Content.New()
                    .Template(Block.New("if(column == c)").Block(Content.New()
                        .Line("return true;")
                    ))
                ))
                .Line("return false;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Column Values")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const columnValues = (data, columns) => ").Block(Content.New()
                .Line("let properties = {};")
                .Template(Block.New("for (const [column, value] of Object.entries(data)) ").Block(Content.New()
                    .Template(Block.New("if(validateColumn(column, columns))").Block(Content.New()
                        .Line("properties[column] = value;")
                    ))
                ))
                .Line("return properties;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Column Where")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const columnWhere = (properties, column, value, columns, operator) => ").Block(Content.New()
                .Template(Block.New("if(column != undefined && value != undefined)").Block(Content.New()
                    .Template(Block.New("if(validateColumn(column, columns))").Block(Content.New()
                        .Template(Block.New("if(!('where' in properties))").Block(Content.New()
                            .Line("properties['where'] = {};")
                        ))
                        .Line("properties['where'][operator] = [{[column] : value}];")
                    ))
                ))
                .Line("return properties;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Column And Where ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const columnAndWhere = (properties, column, value, columns) => ").Block(Content.New()
                .Line("return columnWhere(properties, column, value, columns, Op.and);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Column Or Where ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const columnOrWhere = (properties, column, value, columns) => ").Block(Content.New()
                .Line("return columnWhere(properties, column, value, columns, Op.or);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Column Limit ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const columnLimit = (properties, size, limit) => ").Block(Content.New()
                .Template(Block.New("if(size != undefined && limit != undefined)").Block(Content.New()
                    .Line("properties['offset'] = Number(size) * (Number(limit) - 1);")
                    .Line("properties['limit'] = Number(size);")
                ))
                .Line("return properties;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Page Limit ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const pageLimit = (properties, size, limit) =>").Block(Content.New()
                .Template(Block.New("if(size != undefined && limit != undefined)").Block(Content.New()
                    .Line("properties['page'] = Number(limit);")
                    .Line("properties['size'] = Math.ceil(properties['count'] / size);")
                ))
                .Line("return properties;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Column Where Limit ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const columnWhereLimit = (properties, column, value, size, limit, columns) => ").Block(Content.New()
                .Line("return columnAndWhere(columnLimit(properties, size, limit), column, value, columns);")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Search Foreign Key ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const searchForeignKey = async(values, foreignRepository, column) => ").Block(Content.New()
                .Template(Block.New("if(isDictionary(values))").Block(Content.New()
                    .Line("return (await foreignRepository.create(values))[column];")
                ))
                .Line("return values;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Set Foreign Key ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const setForeignKey = async(values, foreignRepository, column, value) => ").Block(Content.New()
                .Template(Block.New("if(values != undefined)").Block(Content.New()
                    .Line("return await foreignRepository.create(addProperties(values, column, value));")
                ))
                .Line("return values;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Create Or Update ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const createOrUpdate = async(ModelInsatance, values, columns) => ").Block(Content.New()
                .Line("return ModelInsatance.set(columnValues(values, columns)).save();")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Data Response ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const dataResponse = (data) => ").Block(Content.New()
                .Line("return data.length == 0 ? {} : data.length == 1 ? data[0] : data;")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Funtions ")
            .Line(" *******************************************/").Line(1)
            .Template(Block.New("const Repository = ").Block(Content.New()
                .Line("isDictionary,")
                .Line("parseArray,")
                .Line("addProperties,")
                .Line("validateProperties,")
                .Line("validateColumn,")
                .Line("dataResponse,")
                .Line("columnValues,")
                .Line("columnWhere,")
                .Line("columnAndWhere,")
                .Line("columnOrWhere,")
                .Line("columnLimit,")
                .Line("columnWhereLimit,")
                .Line("pageLimit,")
                .Line("searchForeignKey,")
                .Line("setForeignKey,")
                .Line("createOrUpdate")
            )).Line(1)
            .Line("/*******************************************")
            .Line(" * Export ")
            .Line(" *******************************************/").Line(1)
            .Line("export default Repository;")
        .content(), false);
    }
}
