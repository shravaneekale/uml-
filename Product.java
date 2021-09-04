package POS;

public interface Product {

    /**
     *   @return price, returns the price of the product
     */
    public double price();
    
    /**
     *   @return name, returns the name of the product
     */
    public String name();   
    
    /**
     *   @return details, returns the details of the product
     */
    public String details();
    
    /**
     *   @return item, pruchases the product (creates a purchase item)
     */
    public PurchaseItem purchase(int qty);
    
}
