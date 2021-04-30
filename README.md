# Sem1_2021_CAB302_Group024_eTrade
CAB302 Semester 1 2021 Group 024 Major Project - Electronic Trading Platform.

[Specification](https://blackboard.qut.edu.au/bbcswebdav/pid-9209104-dt-content-rid-38013728_1/courses/CAB302_21se1/Assignment%20Specification%282%29.pdf)

[Marking Criteria](https://blackboard.qut.edu.au/bbcswebdav/pid-9209104-dt-content-rid-38013729_1/courses/CAB302_21se1/Marking%20Criteria%20%28CRA%29_%20Electronic%20Asset%20Trading%20Platform.pdf)

# Notice Board: Milestone #1 Submission ([Link](https://blackboard.qut.edu.au/webapps/assignment/uploadAssignment?content_id=_9209123_1&course_id=_154919_1&group_id=&mode=view))
__Milestone #1 is due at the end of Week 8. Only one member submits__ and the submission will need to cover:
- The requirement documents as it currently stands
- The detailed design document as it currently stands.
- The current plan for the next 2 weeks (sprint planning). Keep this realistic and achievable; and describe what each team member will be doing in that time.
- A video showing the progress (including showing the documents and the plan for the next
2 weeks.) Maximum length: 4 minutes.

The client may not have time to read the documents, so make sure __everything is covered in the video__. You can then use this submission to get some useful feedback on how you are going, to check that you are on the right track etc.

The maximum length for this video is __4 minutes__. If you submit a longer video, we will watch the first 4 minutes of it and give you marks based on that. You may want to rehearse what you are going to say and present in the video to keep within this time limit.

| Mark | Criterion | Status |
| --- | --- | --- |
| 1.5 |  The requirements document covers a substantial fraction of the client's requirements | Draft [Requirements](docs/Requirements/README.md)|
| 1.5 | The detailed design addresses many of the identified requirements, with some classes explicitly documented | Needs composing |
| 1   |  Plan for next 2 weeks is substantial, but also realistic and achievable | Decide in week 8's meeting |
| 1   |  Plan for next 2 weeks includes what each group member will be doing | Decide in week 8's meeting |

# Table of content
1. [Documents](Documents)
    1. [Installation Guide](docs/Installation.md)
    2. [Requirements](docs/Requirements/README.md)
    3. [Database](Documents/Database)
        1. [Database Design](Documents/Database/README.md)
        2. [Server-Database Interaction Design](docs/Server_Design/README.md)
    4. Application Design (Class Interaction)
        1. [Client](Documents/Client_Design)
        2. [Server](docs/Server_Design)
        3. [Common Classes](Documents/Common_Classes)
    5. [Contribution Management](Documents/Contribution_Management)
        1. [To Do List](Documents/Contribution_Management/To_Do_List.md)
        2. [Contribution Details](Documents/Contribution_Management/Contribution_Details.md)
2. [Roadmap](#roadmap)

# ROADMAP

This road map is updated every week.

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
        - [ ] Profile: Has labels to display username, name, organisations, role & password field to change password
    - [ ] Admin Tabs:
        - [x] Organisations: Can add / edit / remove organisations (name, credit, assets owned)
        - [x] Assets: Can add / edit / remove assets (name, description)
        - [x] Users: Can add / edit /remove users (name, username, password, organisation, role)
- [ ] Client can interact with a mock class that contains all the organisation related data (balance, stock, orders)

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

- [ ] [Client Design](Documents/Client_Design)
- [ ] [Server Design](docs/Server_Design)
- [ ] [Common Classes](Documents/Common_Classes)

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
    - [ ] Place orders to sell assets using organisation's assets at a certain price & quantity
    - [ ] Place orders to buy assets (regardless of their availability on the market)
    - [ ] View their organisation's orders & remove unreconciled orders
    - [ ] Change own password

Logic & Automation:
- [ ] Client
    - [ ] Update view regularly
- [ ] Server
    - [ ] Matching buy-sell orders to reconcile & update database

## Phase 2

### WEEK 9 (3/5 - 9/5)

### WEEK 10 (10/5 - 16/5) _Milestone #2: 16/5_

## Phase 3

### Week 11 (17/5 - 23/5)

### Week 12 (24/5 - 30/5)

### Week 13 (31/5 - 6/6) (Final Due 6/6/2021)
