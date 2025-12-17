public class Account {
    private Customer owner;
    private double balance;

    public Account(Customer owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void apply(Transaction tx) {
        if (tx.getFrom() == this) {
            balance -= tx.getAmount();
        }
        if (tx.getTo() == this) {
            balance += tx.getAmount();
        }
    }
}