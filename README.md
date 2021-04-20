# Sem1_2021_CAB302_Group024_eTrade
CAB302 Semester 1 2021 Group 024 Major Project - Electronic Trading Platform

# Table of content
1. [Documents](Documents)
    1. [Installation Guide](Documents/Installation.md)
    2. [Requirements](Documents/Requirements/README.md)
    3. [Database](Documents/Database)
        1. [Database Design](Documents/Database/README.md)
        2. [Server-Database Interaction Design](Documents/Server_Design/README.md)
    4. Application Design (Class Interaction)
        1. [Client](Documents/Client_Design)
        2. [Server](Documents/Server_Design)
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
- [ ] Implement common data types that are used by both the server (as package of information) and client (as stored information to view)
    - [ ] Each has methods to generate request strings, based on the action.
    - [ ] Design & Implement the [data classes](Documents/Common_Classes/README.md)
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
- [ ] Client can interact with a mock class that contains all the organisation related data (balance, stock, orders)
    - [ ] User Tabs:
        - [ ] Sell
        - [ ] Buy
        - [ ] History
        - [ ] Profile
    - [ ] Admin Tabs (GUI Only)
        - [ ] Organisations
        - [ ] Assets
        - [ ] Users

### WEEK 8 (26/4 - 2/5) _Milestone #1: 2/5_
_Client - Server interaction via Socket; Update Document_

- [ ] Impliment Socket communication on both sides
___Milestone #1 Checklist___

Documentation:
- [ ] [Requirement](Documents/Requirements)

In each of the design below, include:
- Class Name
- Class Methods and what each of them does
- UML Class Diagram (to show class interaction)

- [ ] [Client Design](Documents/Client_Design)
- [ ] [Server Design](Documents/Server_Design)
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
