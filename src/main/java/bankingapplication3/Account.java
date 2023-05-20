package bankingapplication3;

// interface จะมีแต่ชื่อ method
public interface Account {
    public void deposit(double amount);
    public void withdraw(double amount);
    public double getBalance();
    public String getAccountType();
    public int getAccountNumber();
    public String getAccountName();

}
