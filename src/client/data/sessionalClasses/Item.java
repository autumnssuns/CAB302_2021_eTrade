package client.data.sessionalClasses;

public class Item {
    final String name;
    int quantity;

    public Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public void add(int quantity){
        this.quantity += quantity;
    }

    public String getName() {return name;}
    
    public int getQuantity() {
        return quantity;
    }

    public boolean outOfStock(){
        return quantity == 0;
    }

    public CartItem moveToCart(int quantity, float price){
        this.quantity -= quantity;
        return new CartItem(name, quantity, price);
    }
}