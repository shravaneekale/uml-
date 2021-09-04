package POS;

public class TestPurchaseItem implements PurchaseItem {
    
    private Product p;
    private int qty;
    
    public TestPurchaseItem(Product p, int qty) {
        this.p = p;
        this.qty = qty;
    }
    
    public Product product() {
        return p;
    }
    
    public int qty() {
        return qty;
    }
}
