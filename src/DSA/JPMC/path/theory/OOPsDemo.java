package DSA.JPMC.path.theory;

// 1. ABSTRACTION: Hide implementation details. Expose only what's necessary.
abstract class BankAccount {
    private String accountNumber;
    
    // 2. ENCAPSULATION: Protect state. 'balance' can only be modified through controlled methods.
    private double balance; 

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) this.balance += amount;
    }

    public double getBalance() { return this.balance; }

    // Abstract method forcing child classes to provide their own implementation
    public abstract void calculateInterest(); 
}

// 3. INHERITANCE: SavingsAccount reuses properties from BankAccount
class SavingsAccount extends BankAccount {
    public SavingsAccount(String accountNum, double balance) { super(accountNum, balance); }

    // 4. POLYMORPHISM (Method Overriding): Unique behavior for the same method signature
    @Override
    public void calculateInterest() {
        System.out.println("Savings Interest: Adding 4% -> Current Balance: " + getBalance());
    }
}

class CorporateAccount extends BankAccount {
    public CorporateAccount(String accountNum, double balance) { super(accountNum, balance); }

    @Override
    public void calculateInterest() {
        System.out.println("Corporate Interest: Adding 1% -> Current Balance: " + getBalance());
    }
}

public class OOPsDemo {
    public static void main(String[] args) {
        // Polymorphism in action: Same reference type (BankAccount), different runtime behavior
        BankAccount personal = new SavingsAccount("SAV-123", 1000);
        BankAccount corporate = new CorporateAccount("CORP-999", 50000);

        personal.calculateInterest();
        corporate.calculateInterest();
    }
}