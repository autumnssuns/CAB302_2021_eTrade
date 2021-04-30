__DOCUMENT ONLY__

_This folder contains documents related to the development of the application._

# Document Deliverables
## [Requirements](Requirements/README.md)
## Detailed Design ([Client](Client_Design), [Server](Server_Design) & [Common](../Documents/Common_Classes))
## [Database Design](Database)
## [Network Protocol](Networking)

# Very High Level Description
## Client
- Uses the JavaFX Framework.
- Only interact with one server.

## Server
- Command Line Application.
- Can interact with database via JavaSQL and a SQLite database.
- Can listen & interact with multiple clients. 

## Client - Server Communication
- Uses Socket