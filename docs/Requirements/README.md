# Requirements
_This document contains a list of the client’s requirements._

Synthesise all relevant facts contained within appendices A & B.

|  | Requirement | User Stories | Importance | Progress | Notes |
| --- | --- | --- | --- | --- | --- |
| 1 | Has a graphical user interface. | As a user, I think it’s important to have a nice, friendly GUI interface, not a dusty old command line interface. | HIGH | WIP |
| 2 | Has separate user interfaces for normal users and admins |As a member of the IT Administration team, I need to have special types of user accounts that can do this from the GUI client | HIGH| |
| 2 | User authentication: User needs to log in to use the application | As the leader of an organisational unit, I do not want to accidentally put in a BUY offer for more | HIGH | Implemented |
| 3 | User can see & trades using organisation's credits & assets |As the leader of an organisational unit, I need all of the members of my team to have access to this system, through their own individual usernames and passwords. I need them all to be trading using the credit balance and assets of the organisational unit they are part of.| HIGH | Mocked |
| 4 | User cannot place orders with higher price / quantity than the organisation can afford |As the leader of an organisational unit, I do not want to accidentally put in a BUY offer for more credits than my organisational unit has, or a SELL offer for more of a particular asset than my organisational unit has | HIGH | |
| 5 | User can see current BUY & SELL offers | As a user, when I am thinking about listing a buy or sell offer for an asset, I want to be able to see what current BUY and SELL offers are currently listed, so that I do not greatly overbid/underbid. | HIGH | Can only see SELL, not BUY |
| 6 | Admin can create new organisational units and edit the units'credits and asset quantity|As a member of the IT Administration team, I require that only we (the IT Administration team) have the ability to create new organisational units | HIGH | |
| 7 | Admin can edit & create new asset |As a member of the IT Administration team, I require that only we (the IT Administration team) have the ability to create new organisational units| HIGH | |
| 8 | Admin can edit & create new user | As a member of the IT Administration team, I can add new users and assign them passwords and assign them to organisational units. We can also add new users with the same level of access, so we can give it to anyone new that joins the IT Administration. | HIGH | |
| 9 | Password are hashed before being sent and is stored in hashed form |As the head of IT Security, I do not want plaintext passwords being sent over our network. At least hash the password before sending it over | HIGH | |
| 10 | Has database server | | HIGH | Designed, Unimplemented | |
|  | Has MariDB database to store user information, asset information, organisational unit information and trade information  | | HIGH | | |
| 11 | User can access and remove a listed order from their organisation |As a user, sometimes after adding an offer I might decide that I want to remove that offer, perhaps to relist it again at a different price. | MEDIUM | |
| 12 | Client & Server can run from a config file |As a systems administrator, I would like the client to read from some kind of configuration file to get the server IP address and port to connect to. | MEDIUM | |
| 13 | User can view price history of asset |As a user, when I am thinking about listing a buy or sell offer for an asset, I want to be able to see what current BUY and SELL offers are currently listed, so that I do not greatly overbid/underbid. | MEDIUM |
| 14 | Price history is graphical |, it would be nice if you could show this on some kind of graph (with the date on the X axis and the price on the Y axis) | LOW | |
| 15 | Notification on trade reconciliation |As a user, when I have the software open and a trade involving my organisational unit is reconciled, I would like it to show a little message somewhere.  | LOW |
| 16 | User can change their password | As a user, it would be nice if I could change my own password without having to ask the IT Administration team to do it for me. | LOW | |
