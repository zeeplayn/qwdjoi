public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();
        Customer customer = new Customer("Alice");

        Account acc1 = bank.openAccount(customer, 500);
        Account acc2 = bank.openAccount(customer, 200);

        System.out.println("Before transfer:");
        System.out.println("Account 1: " + acc1.getBalance());
        System.out.println("Account 2: " + acc2.getBalance());

        bank.transfer(acc1, acc2, 100);   // успешный
        bank.transfer(acc1, acc2, 1000);  // ошибка

        System.out.println("After transfer:");
        System.out.println("Account 1: " + acc1.getBalance());
        System.out.println("Account 2: " + acc2.getBalance());
    }
}