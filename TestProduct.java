package POS;

public class TestProduct implements Product {
    public double price() {
        return 4.00;
    }
    
    public String name() {
        return "TestProduct";
    }
    
    public String details() {
        return "This is a test product";
    }
    
    public PurchaseItem purchase(int qty) {
        return new TestPurchaseItem(this, qty);
    }
}
