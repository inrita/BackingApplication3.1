package bankingapplication3;

// ต้องใส่ @Override ใน method ที่อยู่ใน Interface (คลาสแม่)  ทุกตัว
// implements สืบทอด มาจาก interface มี interface เพื่อลดความซับซ้อนของโค้ด
public class CurrentAccount implements Account {
    private int accountNumber;
    private String accountName;
    private double balance;
    private double minimum;
    private String accountType;

    public CurrentAccount(int accountNumber, String accountName, double balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.minimum = 1000;
    }
    @Override
    public int getAccountNumber() {
        return accountNumber;
    }
    @Override
    public String getAccountName() {
        return accountName;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    // ทำการ overide เพราะ class แม่ เป็น interface
    @Override
    public void deposit(double amount) {
        this.balance = this.balance + amount;
    }
    @Override
    public void withdraw(double amount) {
        this.balance = this.balance - amount;
    }
    @Override
    public double getBalance() {
        return this.balance;
    }
    @Override
    public String getAccountType() {
        return this.accountType;
    }
}
