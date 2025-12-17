import java.util.ArrayList;
import java.util.List;

// 1. Класс Клиент
class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}

// 2. Класс Транзакция (запись о переводе)
class Transaction {
    private Account from;
    private Account to;
    private double amount;
    private boolean successful;

    public Transaction(Account from, Account to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void setSuccessful(boolean status) { this.successful = status; }

    @Override
    public String toString() {
        return String.format("Транзакция: от %s к %s на сумму %.2f | Статус: %s",
                from.getOwner().getName(), to.getOwner().getName(), amount, (successful ? "Успешно" : "Отклонено"));
    }
}

// 3. Класс Счет
class Account {
    private Customer owner;
    private double balance;

    public Account(Customer owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    public double getBalance() { return balance; }
    public Customer getOwner() { return owner; }

    // Метод apply для изменения баланса на основе транзакции
    public boolean apply(Transaction tx, double amount, boolean isCredit) {
        if (isCredit) {
            balance += amount;
            return true;
        } else {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false; // Недостаточно средств
        }
    }
}

// 4. Класс Банк
class Bank {
    public Account openAccount(Customer customer, double initialBalance) {
        System.out.println("Открыт счет для: " + customer.getName() + " с балансом " + initialBalance);
        return new Account(customer, initialBalance);
    }

    public void transfer(Account from, Account to, double amount) {
        System.out.println("\n--- Попытка перевода: " + amount + " ---");

        // Валидация: сумма должна быть больше 0
        if (amount <= 0) {
            System.out.println("Ошибка: Сумма перевода должна быть больше 0");
            return;
        }

        Transaction tx = new Transaction(from, to, amount);

        // Пытаемся списать средства
        if (from.apply(tx, amount, false)) {
            to.apply(tx, amount, true);
            tx.setSuccessful(true);
            System.out.println("Успех: Перевод выполнен.");
        } else {
            tx.setSuccessful(false);
            System.out.println("Ошибка: Недостаточно средств на счету " + from.getOwner().getName());
        }

        System.out.println(tx);
    }
}

// --- ОСНОВНОЙ СЦЕНАРИЙ ---
public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank();

        // 1. Создаем клиента и два счета
        Customer ivan = new Customer("Иван");
        Customer maria = new Customer("Мария");

        Account acc1 = myBank.openAccount(ivan, 1000.0);
        Account acc2 = myBank.openAccount(maria, 500.0);

        // Печать баланса ДО
        printBalances(acc1, acc2);

        // 2. Делаем 1 успешный перевод
        myBank.transfer(acc1, acc2, 300.0);

        // 3. Пытаемся сделать перевод с недостаточным балансом
        myBank.transfer(acc1, acc2, 2000.0);

        // 4. Печать баланса ПОСЛЕ
        System.out.println("\n--- Итоговое состояние ---");
        printBalances(acc1, acc2);
    }

    public static void printBalances(Account a1, Account a2) {
        System.out.println("Баланс " + a1.getOwner().getName() + ": " + a1.getBalance());
        System.out.println("Баланс " + a2.getOwner().getName() + ": " + a2.getBalance());
    }
}