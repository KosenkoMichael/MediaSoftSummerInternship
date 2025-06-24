import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BankAccount {
    private String _ownerName;
    private int _balance;
    private LocalDateTime _openDate;
    private Boolean _isBlocked;
    private String _number;

    public BankAccount(String name) {
        _ownerName = name;
        _balance = 0;
        _openDate = LocalDateTime.now();
        _isBlocked = false;
        _number = generateAccountNumber();
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public Boolean deposit(int amount) {
        if (_isBlocked) {
            return false;
        }
        _balance += amount;
        return true;
    }

    public Boolean withdraw(int amount) {
        if (_isBlocked) {
            return false;
        }
        if (_balance - amount < 0) {
            return false;
        }
        _balance -= amount;
        return true;
    }

    public Boolean transfer(BankAccount otherAccount, int amount) {
        if (_isBlocked || otherAccount._isBlocked) {
            return false;
        }
        if (withdraw(amount)) {
            otherAccount.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return "BankAccount {" +
                "\n  Name: " + _ownerName +
                "\n  Balance: " + _balance +
                "\n  Open Date: " + _openDate.format(formatter) +
                "\n  Is Blocked: " + _isBlocked +
                "\n  Number: " + _number +
                "\n}";
    }

    public Boolean equals(BankAccount otherAccount) {
        if (hashCode() == otherAccount.hashCode()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
