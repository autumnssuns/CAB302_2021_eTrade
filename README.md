# 2021 CAB302 Major Project | Group 24 | Electronic Trading Platform

[Specification](https://blackboard.qut.edu.au/bbcswebdav/pid-9209104-dt-content-rid-38013728_1/courses/CAB302_21se1/Assignment%20Specification%282%29.pdf)

[Marking Criteria](https://blackboard.qut.edu.au/bbcswebdav/pid-9209104-dt-content-rid-38013729_1/courses/CAB302_21se1/Marking%20Criteria%20%28CRA%29_%20Electronic%20Asset%20Trading%20Platform.pdf)

# Workflow
1. [Create an Issue](https://github.com/autumnssuns/Sem1_2021_CAB302_Group024_eTrade/issues)
2. Comment `/cib` on an issue to indicate you are currently working on it. A new branch with the issue number and title is automatically generated.
3. In IntelliJ, fetch and checkout to the automatically created branch.
4. Implement then `Commit and Push`.
5. [Create a Pull Request](https://github.com/autumnssuns/Sem1_2021_CAB302_Group024_eTrade/pulls) and `Merge` if all checks have passed. The issue will automatically close.

# Notice Board: Final Submission on 06/06/2021

# Requirements Progresses (14/18)
- [x] Has a graphical user interface
- [x] Has separated user interfaces for normal users and admins
- [x] User authentication: User needs to log in to use the application
- [x] User can see & trades using organisationalUnit's credits & assets
- [ ] User cannot place orders with higher price / quantity than the organisational unit can afford
- [x] User can see current BUY & SELL offers
- [x] Admin can create new organisational units and edit the units' credits and asset quantity
- [x] Admin can edit & create new asset
- [x] Admin can edit & create new user
- [x] Password are hashed before being sent and is stored in hashed form
- [x] Has a server that can interact with database and return reponse to client's request
- [x] Server can facilitate and match sell-buy order
- [ ] Has server-side database to store user information, asset information, organisational unit information and trade information
- [x] User can access and remove a listed order from their organisational unit
- [ ] Client & Server can run using configurations from a .properties file
- [x] User can view price history of asset. Price history is graphical
- [ ] Notification on trade reconciliation
- [x] User can change their password

# Documents Table of content
1. [Workflow Guide](docs/Workflow)
2. [Installation Guide](docs/Installation.md)
3. [Requirements](docs/Requirements/README.md)
4. [Database](docs/Database)
    1. [Database Design](docs/Database/README.md)
5. Application Design
    1. [Client](docs/Client_Design)
    2. [Server](docs/Server_Design)
    3. [Common Classes](docs/Common_Classes)
6. [Networking](docs/Networking)

