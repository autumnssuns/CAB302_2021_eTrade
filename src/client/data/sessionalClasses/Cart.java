package client.data.sessionalClasses;

import java.util.ArrayList;

public class Cart extends ArrayList<client.data.sessionalClasses.CartItem> {
    public boolean add(CartItem item){
        boolean result = false;

        for(int i = 0; i < this.size(); i++){
            CartItem currentItem = this.get(i);
            if (currentItem.getName() == item.getName() && currentItem.getPrice() == item.getPrice()){
                this.get(i).add(item.getQuantity());
                result = true;
                break;
            }
        }

        result = result ? result : super.add(item);
        return result;
    }

    public void update(String itemName){

    }

    public float getTotalPrice() {
        float total = 0;
        for (CartItem item : this){
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clear(){
        super.clear();
    }
}