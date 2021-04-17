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
| -name: String <br> -balance: Float <br> -stock: Stock <br> -sellOrders: SellOrders <br> -buyOrders: BuyOrders|
| +getName(): String <br> +getBalance(): Float <br> +setBalance() <br> +getStock(): Stock <br> +getSellOrders(): SellOrders <br> +getBuyOrders(): BuyOrders <br> +Update|
