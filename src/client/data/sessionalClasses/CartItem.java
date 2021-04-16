package client.data.sessionalClasses;

public class CartItem extends Item{
    private float price;

    public CartItem(String name, int quantity, float price) {
        super(name, quantity);
        this.price = price;
    }

    public float getPrice() {
        return this.price;
    }

    public float getTotalPrice() {
        return price * quantity;
    }

    public void update(int quantity, float price){
        this.quantity = quantity;
        this.price = price;
    }
}
