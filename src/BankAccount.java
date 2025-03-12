import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final int id;
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    // Constructor initializing account unique ids and initial balance
    public BankAccount(int id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    // Accessors and Mutators
    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
