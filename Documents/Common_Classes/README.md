# Common classes 
This package contains design of classes that are used by both server and client.

Includes class design: Designs of the (public) Java classes that will comprise your programs:
- Descriptions of all public methods and fields.
- Descriptions of the arguments each method takes and what the method returns.
- Any assumptions (preconditions and postconditions).
- Any checked exceptions that may be thrown by the method, and the circumstances under which the exceptions will be thrown.
- How these classes interact with each other (UML Diagram).

## As Normal User
1. class User: stores the username & role of the user.

| User        |
| ------------- |
| -name: String <br> -username: String <br> -role: String <br> -organisation: Organisation|
| +getUsername(): String <br> +getRole(): String <br> +getOrganisation(): Organisation|

User(String username): Connects to server and fetch all information related to the user.

2. class Organisation: stores all the data related to an organisation.

| Organisation |
| ------------ |
| -name: String <br> -credit: Credit <br> -stock: Stock <br> -sellOrders: SellOrders <br> -buyOrders: BuyOrders|
| +getName(): String <br> +getCredit(): Float <br> +getStock(): Stock <br> +getSellOrders(): SellOrders <br> +getBuyOrders(): BuyOrders|

3. Base class OrganisationData: base class for other data classes used by Organiation

| OrganisationData |
|---|
| +Update(): OrganisationData <br> +Request(): void|

`Update() `
- Requests server to query related data

`Commit()`
- Requests server to change related data

Children of OrganisationData

| Credit |
| --- |
| -balance: Float |
| +get() <br> +set(Float newBalance) <br> +add(Float amount) <br> +remove(Float amount) |
