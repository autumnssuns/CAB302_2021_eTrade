# Documents Table of Content
1. [Requirements](Requirements/README.md)
2. Application Design
    1. [Client](Client_Design)
    2. [Server](Server_Design)
    3. [Common](Common_Classes)
    4. [Detailed Class Description](Detailed_Class_Description/index.html)
3. [Database Design](Database)
4. [Network Protocol](Networking)

# System Architecture & High Level Design Summary
The application embraces a typical Model - View - Controller (MVC) architecture, with the Client (User-facing application) consists of a View and a Controller. Meanwhile, the Server consits of the Model which can interact with a MySQL server.
- View: Displays using data from controller
- Controller: Stores data for View and can send requests to server to update data
- Model: Talks to database and send responses back to controller.

![System Architecture MVC](CAB302_Architecture.png)
