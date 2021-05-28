# Requirements
_This document contains a list of the client’s requirements._

Synthesise all relevant facts contained within appendices A & B.

|  | Requirement | User Stories | Importance | Progress | Notes |
| --- | --- | --- | --- | --- | --- |
| 1 | Has a graphical user interface. | As a user, I think it’s important to have a nice, friendly GUI interface, not a dusty old command line interface. | HIGH | WIP |
| 2 | Has separate user interfaces for normal users and admins |As a member of the IT Administration team, I need to have special types of user accounts that can do this from the GUI client | HIGH| Implemented |
| 3 | User authentication: User needs to log in to use the application | As the leader of an organisational unit, I do not want to accidentally put in a BUY offer for more | HIGH | Implemented |
| 4 | User can see & trades using organisationalUnit's credits & assets |As the leader of an organisational unit, I need all of the members of my team to have access to this system, through their own individual usernames and passwords. I need them all to be trading using the credit balance and assets of the organisational unit they are part of.| HIGH | Mocked |
| 5 | User cannot place orders with higher price / quantity than the organisationalUnit can afford |As the leader of an organisational unit, I do not want to accidentally put in a BUY offer for more credits than my organisational unit has, or a SELL offer for more of a particular asset than my organisational unit has | HIGH | Implemented |
| 6 | User can see current BUY & SELL offers | As a user, when I am thinking about listing a buy or sell offer for an asset, I want to be able to see what current BUY and SELL offers are currently listed, so that I do not greatly overbid/underbid. | HIGH | Established Data Layer, not GUI |
| 7 | Admin can create new organisational units and edit the units'credits and asset quantity|As a member of the IT Administration team, I require that only we (the IT Administration team) have the ability to create new organisational units | HIGH | Implemented |
| 8 | Admin can edit & create new asset |As a member of the IT Administration team, I require that only we (the IT Administration team) have the ability to create new organisational units| HIGH | Implemented |
| 9 | Admin can edit & create new user | As a member of the IT Administration team, I can add new users and assign them passwords and assign them to organisational units. We can also add new users with the same level of access, so we can give it to anyone new that joins the IT Administration. | HIGH | Implemented |
| 10 | Password are hashed before being sent and is stored in hashed form |As the head of IT Security, I do not want plaintext passwords being sent over our network. At least hash the password before sending it over | HIGH | Implemented in Server |
| 11 | Has a server that can interact with database and return reponse to client's request  | We require this to be implemented with a client-server model, where we run 1 server (which keeps track of every organisational unit’s assets, credit balance and all trades.) and clients connect to this server to list trades| HIGH | | |
|12 | Server can facilitate and match sell-buy order| Your software will periodically check and reconcile all outstanding trades| HIGH| Mocked, Actual server not finished |  | 
| 13 | Has MariaDB database to store user information, asset information, organisational unit information and trade information  | As a systems administrator, I require the following things to be stored in a MariaDB or PostgreSQL or SQLite3 database on the server, so they are all kept in one place and they can be backed up easily| HIGH | Has MySQL Database | |
| 14 | User can access and remove a listed order from their organisationalUnit |As a user, sometimes after adding an offer I might decide that I want to remove that offer, perhaps to relist it again at a different price. | MEDIUM | Implemented |
| 15 | Client & Server can run from a config file |As a systems administrator, I would like the client to read from some kind of configuration file to get the server IP address and port to connect to. | MEDIUM | |
| 16 | User can view price history of asset |As a user, when I am thinking about listing a buy or sell offer for an asset, I want to be able to see what current BUY and SELL offers are currently listed, so that I do not greatly overbid/underbid. | MEDIUM | Mocked |
| 17| Price history is graphical |, it would be nice if you could show this on some kind of graph (with the date on the X axis and the price on the Y axis) | LOW | Mocked |
| 18 | Notification on trade reconciliation |As a user, when I have the software open and a trade involving my organisational unit is reconciled, I would like it to show a little message somewhere.  | LOW |
| 19 | User can change their password | As a user, it would be nice if I could change my own password without having to ask the IT Administration team to do it for me. | LOW | Mocked |
