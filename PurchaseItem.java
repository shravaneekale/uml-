package POS;

public interface PurchaseItem {
    
    /**
     *   @return product, returns the product
     */
    public Product product();
    
    /**
     *   @return qty, returns number of products purchased
     */
    public int qty();
    
    public default String getItem() { return product().name(); }
    public default int getQty() { return qty(); }
    public default double getPrice() { return product().price(); }
    public default double getTotal() { return product().price()*qty(); } 
}
