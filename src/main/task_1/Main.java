public class Main {
    public static void main(String[] args) {
        BankAccount firstAccount = new BankAccount("Michael");
        BankAccount secondAccount = new BankAccount("Bob");

        System.out.println(firstAccount.toString());
        System.out.println(secondAccount.toString());

        firstAccount.deposit(1000);
        secondAccount.deposit(3000);

        System.out.println(firstAccount.toString());
        System.out.println(secondAccount.toString());

        secondAccount.transfer(firstAccount, 1000);

        System.out.println(firstAccount.toString());
        System.out.println(secondAccount.toString());

        System.out.println(firstAccount.equals(secondAccount));

        System.out.println(firstAccount.hashCode());
        System.out.println(secondAccount.hashCode());

    }
}