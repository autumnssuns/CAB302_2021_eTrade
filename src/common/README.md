This package contains classes that are used by both server and client.

Data required from client -> Server should return these objects

# Normal User
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
| +Download(): void <br> +Upload(): void|

| Method | Function |
| --- | --- |
| +Update(): void | Request server to query related data |
| +Commit(): void | Request server to change related data |

Children of OrganisationData

| Credit |
