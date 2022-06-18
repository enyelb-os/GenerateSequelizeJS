# GenerateSequalizeJS

Simple generator models for sequalize.js

### Example comand
```*.java

java -jar .\sequalize.jar --user root --pass root --name database_name

--host -h
--port
--user -u
--pass -p
--name -n
--type -t

```

### Example Express project
```*.js

import express from 'express';
import routes from './DatabaseName/default.js';

const app = express();

app.use("/", routes);

app.listen(11800, () => {
  console.log(`Servidor corriendo en puerto 11800`);
});
```

### Example Api
```*.js

GET List                        /table_name/list
GET List Pagination             /table_name/list/:size/:limit
GET List By Column              /table_name/list/by/:column/:value
GET List By Column Pagination   /table_name/list/:size/:limit/by/:column/:value

POST Create                     /table_name/create
Example body
{
    "column_name" : 1,                // is primary key, is optional for update
    "column_name":{                   // is foreign key, value is object for search primary key
        "column": "value"
    },
    "column_name":"1",                // is value, set value literal
}

```
