import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TransactionSystem {
    private final Map<Integer, BankAccount> accounts;

    // Constructor initialising account mapping from a list of accounts
    public TransactionSystem(List<BankAccount> accountList) {
        accounts = new HashMap<>();
        for (BankAccount account : accountList) {
            accounts.put(account.getId(), account);
        }
    }

    // Transferring money from one account to another
    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);

        // Locking accounts in a safe order to avoid deadlock
        BankAccount firstLock = fromAccountId < toAccountId ? fromAccount : toAccount;
        BankAccount secondLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        // Locking both accounts to ensure exclusive access during transaction
        firstLock.lock();
        secondLock.lock();

        try {
            // Checking if the fromAccount / sender's account has sufficient balance
            if (fromAccount.getBalance() < amount) {
                System.out.println("Insufficient balance in Account " + fromAccountId);
                return false;
            }
            // Safely modifying balances
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transferred $" + amount + " from Account " + fromAccountId + " to Account " + toAccountId);
            return true;
        } finally {
            secondLock.unlock();
            firstLock.unlock();
        }
    }

    // Reversing a transaction
    public void reverseTransaction(int fromAccountId, int toAccountId, double amount) {
        System.out.println("Reversing transaction: $" + amount + " from Account " + toAccountId + " to Account " + fromAccountId);
        transfer(toAccountId, fromAccountId, amount);
    }

    // Printing all account balances
    public void printAccountBalances() {
        accounts.values().forEach(account ->
                System.out.println("Account " + account.getId() + ": $" + account.getBalance()));
    }
}
