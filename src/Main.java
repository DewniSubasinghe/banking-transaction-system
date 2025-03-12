import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Create bank accounts
        BankAccount account1 = new BankAccount(1, 500);
        BankAccount account2 = new BankAccount(2, 300);
        BankAccount account3 = new BankAccount(3, 200);

        // Initialize the transaction system
        TransactionSystem transactionSystem = new TransactionSystem(Arrays.asList(account1, account2, account3));

        // Create threads for transactions
        Thread thread1 = new Thread(() -> transactionSystem.transfer(1, 2, 100));
        Thread thread2 = new Thread(() -> transactionSystem.transfer(2, 3, 200));
        Thread thread3 = new Thread(() -> transactionSystem.transfer(3, 1, 50));
        Thread thread4 = new Thread(() -> {
            System.out.println("Account 1 Balance: $" + account1.getBalance());
            System.out.println("Account 3 Balance: $" + account3.getBalance());
        });

        // Starting the threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // Waiting for threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Printing final account balances
        transactionSystem.printAccountBalances();
    }
}
