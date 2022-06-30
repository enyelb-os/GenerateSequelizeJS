# GenerateSequelizeJS

Simple generator models for sequelize.js

### Example comand
```*.java

java -jar .\sequelize.jar --user root --pass root --name database_name

--host -h
--port
--user -u
--pass -p
--name -n
--type -t

--express -ex // integracion con express
--jwt         // integracion con JWT
--modules -m  // permitir multiples modulos

```

### Src Project
```*.js
database                  // Name data base
├─ scheme                 // Name scheme (default)
│  ├─ base                // Base code porject
│  │  ├─ controller       // Controllers base
│  │  │  └─ table.js      // All files (table names) 
│  │  ├─ model            // Models base
│  │  │  └─ table.js      // All files (table names)
│  │  ├─ repository       // Repositories base
│  │  │  └─ table.js      // All files (table names)
│  │  └─ route            // Routes base
│  │     └─ table.js      // All files (table names)
│  ├─ controller          // Modifiable controllers
│  │  ├─ table.js         // All files (table names)
│  │  └─ login.js         // Integration jwt login
│  ├─ model               // Modifiable model
│  │  └─ table.js         // All files (table names)
│  ├─ repository          // Modifiable repositories
│  │  └─ table.js         // All files (table names)
│  ├─ route               // Modifiable routes
│  │  ├─ table.js         // All files (table names)
│  │  └─ login.js         // Route for login
│  └─ validation          // Validationes 
│     ├─ controller       // Controllers
│     │  └─ table.js      // All files (table names)
│     ├─ model            // Models
│     │  └─ table.js      // All files (table names)
│     └─ repository       // Repositories
│        └─ table.js      // All files (table names)
├─ middleware             // Middlewares
│  └─ jwt.js              // integration JWT
├─ database.js            // Configuration database
├─ scheme.js              // Configuration routes
└─ repository.js          // Methods base
```

### Example Express project
```*.js

import express from 'express';
import tools.gnzlz.javascript.express.routes from './DatabaseName/default.js';

const app = express();

app.use("/", tools.gnzlz.javascript.express.routes);

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
