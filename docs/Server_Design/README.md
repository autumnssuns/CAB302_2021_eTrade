# Server Side Classes - Database Interaction
Includes class design: Designs of the (public) Java classes that will comprise your programs:
- Descriptions of all public methods and fields.
- Descriptions of the arguments each method takes and what the method returns.
- Any assumptions (preconditions and postconditions).
- Any checked exceptions that may be thrown by the method, and the circumstances under which the exceptions will be thrown.
- How these classes interact with each other (UML Diagram).

These classes are used to interact with a database. Structure inspired by [Week 7 Tutorial](https://github.com/qut-cab302/prac07). 

There are 4 categories of classes:
- [`DBConnection`](#dbconnection-class): Connects the server to a database schema.
- [`DataSource`](#data-source-classes): Executes prepared SQL statement to interact with database.
- [`Data`](#data-classes): Uses a DataSource and its methods to retrieve data.
- [`DataType`](#data-type-classes): Stores detailed information of certain objects.

## DBConnection Class
_Connects the server to a database schema._

|DBConnection|
|---|
|-<ins>`instance`</ins>: Connection|
|-`DBConnection()` <br> +<ins>`getInstance()`</ins>: Connection|

`private DBConnection()`
- Reads from a config file to retrieve database information (url, username, password, schema).
- Uses DriverManager to connect to database & override `instance`.

`getInstance()`
- If there is no current `instance`, creates one.
- Returns `instance`.

## Data Source Classes
_Executes prepared SQL statement to interact with database._

Base Class:

|DataSource|
|---|
|+<ins>`CREATE_TABLE`</ins>: String <br> -<ins>`connection`</ins>: Connection <br> #<ins>`INSERT`</ins>: String <br> #<ins>`GET`</ins>: String <br> #<ins>`DELETE`</ins>: String <br> #`add`: PreparedStatement <br> #`get`: PreparedStatement <br> #`delete`: PreparedStatement|
|-`DataSource()` <br> +`add(DataType data)` <br> +`get(Object key)` <br> +`delete(Object key)` <br> +<ins>`close()`</ins>|

`DataSource()`
- Connects to database.

`close()`
- Close connection to database.

Each of the following classes extends the `DataSource` class and should have at least 3 methods:
- `add(DataType data)`: Add a new row into the database
- `get(Object key)`: Retrieve the row associated to the key
- `delete(Object key)`: Delete the row associated to the key

The classes below inherits `DataSource` and can modify the SQL Strings (`INSERT`, `GET`, `DELETE`) to adapt to each table.

### UsersDataSource
|UsersDataSource|
|---|
||
||

### OrganisationsDataSource
|OrganisationsDataSource|
|---|
||
||

### AssetsDataSource
|OrganisationsDataSource|
|---|
||
||

### OrdersDataSource
|OrganisationsDataSource|
|---|
||
||

## Data Classes
_Uses a DataSource and its methods to retrieve data._


## Data Type Classes
_Stores detailed information of certain objects._
These classes will also be used by the client, see [Common_Classes Design](../../Documents/Common_Classes).