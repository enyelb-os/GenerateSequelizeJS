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

### Example Api Routes
```*.js

// default

GET List                        /table_name/list
GET List Pagination             /table_name/list/:size/:limit
GET List By Column              /table_name/list/by/:column/:value
GET List By Column Pagination   /table_name/list/:size/:limit/by/:column/:value
POST Create                     /table_name/create

// if it is multiple modules

GET List                        /database_name/scheme_name/table_name/list
GET List Pagination             /database_name/scheme_name/table_name/list/:size/:limit
GET List By Column              /database_name/scheme_name/table_name/list/by/:column/:value
GET List By Column Pagination   /database_name/scheme_name/table_name/list/:size/:limit/by/:column/:value
POST Create                     /database_name/scheme_name/table_name/create
```

### JSON Body create
```*.json
{
    "column_name" : 1,                // if it is primary key, is optional for update
    "column_name":{                   // if it is foreign key, if "value" is an object it will be used for the main key search
        "column": "value"
    },
    "column_name":"1",                // if it is value, set value literal
    "table_name": {                   // 
      "column": "value"
    }
}
```

### src/database/scheme/validation/controller
```*.js
/*******************************************
 * Before Find
 *******************************************/

const beforeFind = async (req, res, next) => {
  //http404(req, res);                          // add for hidden route    
  validateAuthenticate(req, res);               // Validate Autentication with JWT
  next();
}

/*******************************************
 * Before Create 
 *******************************************/

const beforeCreate = async (req, res, next) => {
  validateAuthenticate(req, res);                 // Validate Autentication with JWT
  next();
}
```

### src/database/scheme/validation/model
```*.js

/*******************************************
 * Validation 
 *******************************************/
 
const TableName = {
  column_name: {
      // validations sequelize 
  }
  ...
  ...
}
```
