package DSA.JPMC.path.theory;

import java.math.BigDecimal;

// 1. The Strategy Interface
interface PaymentStrategy {
    void pay(BigDecimal amount);
}

// 2. Concrete Strategy A (Credit Card)
class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    public CreditCardStrategy(String cardNumber) { this.cardNumber = cardNumber; }
    
    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Paid $" + amount + " using Credit Card ending in " + cardNumber.substring(12));
    }
}

// 3. Concrete Strategy B (UPI/Crypto)
class UpiStrategy implements PaymentStrategy {
    private String upiId;
    public UpiStrategy(String upiId) { this.upiId = upiId; }
    
    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Paid $" + amount + " using UPI ID: " + upiId);
    }
}

// 4. Context Class (Uses the strategy)
class ShoppingCart {
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public void addAmount(BigDecimal amount) {
        totalAmount = totalAmount.add(amount);
    }

    // The method takes the strategy dynamically at runtime
    public void checkout(PaymentStrategy paymentMethod) {
        paymentMethod.pay(totalAmount);
    }
}

public class StrategyPatternDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addAmount(new BigDecimal("150.00"));
        
        // Runtime decision: User selects UPI
        cart.checkout(new UpiStrategy("user@jpmc"));
        
        // Runtime decision: User selects Credit Card
        cart.checkout(new CreditCardStrategy("1234567890124444"));
    }
}