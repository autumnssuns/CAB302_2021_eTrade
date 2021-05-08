# ROADMAP

This road map is updated every week. After Milestone #1, it was replaced by the `Issues` feature of GitHub.

## Phase 1

### WEEK 4 (26/3 - 28/3)
_Assignment Released - Brief team discussion on requirements_

- [x] Team Formation (Dan, Daniel, Lyn, Rodo) - Alphabetically sorted
- [x] Requirement Draft

### WEEK 5 (29/3 - 4/4)
_Initial design - Database Schema & GUI_
- [x] Draft ORM
- [x] GUI Layout Draft Design

### WEEK 6 (12/4 - 18/4)
_GUI implementation using JavaFX & Login System_
- [x] GUI template
- [x] Login System
- [x] User GUI
    - [x] Basic Functions:
        - [x] Interaction: Multiple Tabs & Change Tab on click
        - [x] Interaction: Log Out
    - [x] Sell GUI Features
        - [x] Display: Current assets - Name, Quantity in stock (No Database - Mock List)
        - [x] Interaction: Add asset to shipping cart - Enter Price & Quantity to sell, commit on button click
- [x] Creat a GitHub repository for the project
- [x] Split into 2 teams: Client (Lyn, Dan) ; Server (Rodo, Daniel)

### -> WEEK 7 (19/4 - 25/4)
_Data Type & Server implementation on database interaction_
- [x] Create a roadmap to log future progress (Past weeks were filled with already made progress)
- [x] Implement common data types that are used by both the server (as package of information) and client (as stored information to view)
- [ ] Server can interact with database
    - [ ] Command-Line Application that loops indefinitely and
        - [ ] Display texts
        - [ ] Save current display into a log file with timestamp in name
        - [ ] Can take user input to execute command
        - [ ] Is multithreaded to both display and listen for input at the same time
    - [ ] SQL Scripts to interact with database
        - [x] Database Creation
        - [ ] Query from tables
        - [ ] Update tables
    - [ ] Implement methods for server, based on prepared SQL Scripts and use JavaSQL to interact with database
- [ ] Finish up GUI Implementation (No Database Interaction Needed)
    - [ ] User Tabs:
        - [ ] Sell: Can display assets, add assets to cart and checkout
        - [ ] Buy: Same as above
        - [ ] History: Can display orders (id, type, asset, unit price, placed_quantity, resolved_quantity, date_placed, status) and cancel pending orders.
        - [ ] Profile: Has labels to display username, name, organisationalUnits, role & password field to change password
    - [ ] Admin Tabs:
        - [x] Organisations: Can add / edit / remove organisationalUnits (name, credit, assets owned)
        - [x] Assets: Can add / edit / remove assets (name, description)
        - [x] Users: Can add / edit /remove users (name, username, password, organisationalUnit, role)
- [ ] Client can interact with a mock class that contains all the organisationalUnit related data (balance, stock, orders)

### WEEK 8 (26/4 - 2/5) _Milestone #1: 2/5_
_Client - Server interaction via Socket; Update Document_

- [ ] Impliment Socket communication on both sides
___Milestone #1 Checklist___

Documentation:
- [ ] [Requirement](docs/Requirements)

In each of the design below, include:
- Class Name
- Class Methods and what each of them does
- UML Class Diagram (to show class interaction)

- [ ] [Client Design](docs/Client_Design)
- [ ] [Server Design](docs/Server_Design)
- [ ] [Common Classes](docs/Common_Classes)

User Interaction:
- [ ] Common
    - [ ] Login with username & password in database
- [ ] As Admin
    - [ ] Has a default username & password on first run of server / no database
    - [ ] Add & Edit
        - [ ] Asset(s): 
        - [ ] Organisation(s): name, credits and assets
        - [ ] User(s): username & password
- [ ] As User
    - [ ] Login with username and password given by admin
    - [ ] Place orders to sell assets using organisationalUnit's assets at a certain price & quantity
    - [ ] Place orders to buy assets (regardless of their availability on the market)
    - [ ] View their organisationalUnit's orders & remove unreconciled orders
    - [ ] Change own password

Logic & Automation:
- [ ] Client
    - [ ] Update view regularly
- [ ] Server
    - [ ] Matching buy-sell orders to reconcile & update database
