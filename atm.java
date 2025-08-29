import java.util.*;
public class atm {
   public class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    
    public String getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public List<String> getTransactionHistory() { return transactionHistory; }

    public void setBalance(double balance) { this.balance = balance; }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

    private Map<String, Account> accounts;
    private Account currentAccount;
    private Scanner scanner;

    public atm() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeAccounts();
    }

    private void initializeAccounts() {
        Account account1 = new Account("123456", "1234", 1000.0);
        Account account2 = new Account("654321", "4321", 500.0);
        Account account3 = new Account("234567", "4587", 20000);
        accounts.put(account1.getAccountNumber(), account1);
        accounts.put(account2.getAccountNumber(), account2);
        accounts.put(account3.getAccountNumber(), account3);
    }

    public void start() {
        System.out.println("Welcome to SBI ATM");
        
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Thank you for using SBI ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void login() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            if (account.getPin().equals(pin)) {
                currentAccount = account;
                showMenu();
            } else {
                System.out.println("Invalid PIN. Please try again.");
            }
        } else {
            System.out.println("Account not found. Please try again.");
        }
    }

    private void showMenu() {
        while (currentAccount != null) {
            System.out.println("\nATM Menu");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    showTransactionHistory();
                    break;
                case 5:
                    currentAccount = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void checkBalance() {
        System.out.printf("Your current balance is: RS%.2f\n", currentAccount.getBalance());
        currentAccount.addTransaction("Balance checked: Rs" + currentAccount.getBalance());
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: Rs");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds.");
            return;
        }

        currentAccount.setBalance(currentAccount.getBalance() - amount);
        System.out.printf("$%.2f withdrawn successfully.\n", amount);
        currentAccount.addTransaction("Withdrawal: -Rs" + amount);
        System.out.printf("Remaining balance: Rs%.2f\n", currentAccount.getBalance());
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: Rs");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        currentAccount.setBalance(currentAccount.getBalance() + amount);
        System.out.printf("$%.2f deposited successfully.\n", amount);
        currentAccount.addTransaction("Deposit: +Rs" + amount);
        System.out.printf("New balance: Rs%.2f\n", currentAccount.getBalance());
    }

    private void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (currentAccount.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : currentAccount.getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    public static void main(String[] args) {
        atm atm1 = new atm();
        atm1.start();
    }
}
