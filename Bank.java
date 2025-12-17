public class Bank {

    public Account openAccount(Customer customer, double initialBalance) {
        return new Account(customer, initialBalance);
    }

    public boolean transfer(Account from, Account to, double amount) {

        if (amount <= 0) {
            System.out.println("Amount must be positive");
            return false;
        }

        if (from.getBalance() < amount) {
            System.out.println("Insufficient funds");
            return false;
        }

        Transaction tx = new Transaction(from, to, amount);
        from.apply(tx);
        to.apply(tx);

        System.out.println("Transfer successful: " + amount);
        return true;
    }
}