package bankingapplication3;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {
    private String bankName;

    public Bank() {} // สร้างไว้รับค่าเฉยๆ

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return getBankName();
    }

    // จะแสดงข้อมูลทั้งหมด (account) ที่มี
    public void listAccount() {
        Connection con = BankConnection.connect();
        try {
            Statement sta = con.createStatement();
            String sql = "select * from account";
            ResultSet result = sta.executeQuery(sql);

            while (result.next()) {
                System.out.println(result.getString(1) + " " + result.getString(2) + " "
                        + result.getString(3) + " " + result.getString(4));
            }
            System.out.println();

        } catch (SQLException ex) {
            Logger.getLogger(BankConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // เพิ่มข้อมูลลงใน database (insert account)
    public void openAccount(Account account) { // (Account account) เป็น polimorlism methof สามารถรับ object ได้หลายประเภท
        Connection con = BankConnection.connect();
        String sql = "insert into account(accID, accName, accBalance, accType)" + "values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.setString(4, account.getAccountType());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BankConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ลบข้อมูลใน database (delete account)
    public void closeAccount(Account account) {
        Connection con = BankConnection.connect();
        String sql = "delete from account where accID = ? and accType = ?"; // ลบเฉพาะ account ที่เราต้องการ
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountType());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BankConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Update ข้อมูล (ฝากเงินเข้าเงินในบัญชีก็จะมีการ update)
    public void depositMoney(Account account, double amount) {
        account.deposit(amount); //เอาบัญชี (account) นี้มาฝากเงินเข้า (amount)
        System.out.println(account.getBalance());
        Connection con = BankConnection.connect();
        String sql = "update account set accBalance = ? where accID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BankConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Update ข้อมูล (ถอนเงินออกจากบัญชีก็จะมีการ update)
    public void withdrawMoney(Account account, double amount) {
        account.withdraw(amount); //เอาบัญชี (account) นี้มาถอนเงินออก (amount)
        System.out.println(account.getBalance());
        Connection con = BankConnection.connect();
        String sql = "update account set accBalance = ? where accID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BankConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account getAccount(int accountNumber, String acccountType) {
        Connection con = BankConnection.connect();
        Account account = null;
        String sql = "select * from account where accID = ? and accType = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, acccountType);
            ResultSet result = preparedStatement.executeQuery();

            result.next();
            String accountName = result.getNString(2);
            double balance = result.getDouble(3);
            String accountType = result.getNString(4);

            if (accountType.equals("SavingsAccount")) {
                account = new SavingsAccount(accountNumber, accountName, balance);
            } else if (accountType.equals("CurrentAccount")) {
                account = new CurrentAccount(accountNumber, accountName, balance);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BankConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }
}
