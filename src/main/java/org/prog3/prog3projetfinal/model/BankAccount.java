package org.prog3.prog3projetfinal.model;

public class BankAccount implements FinancialAccount {
    private String id;
    private String holderName;
    private Bank bankName;
    private int bankCode;
    private int bankBranchCode;
    private int bankAccountNumber;
    private int bankAccountKey;
    private double amount;

    @Override
    public String getId() {
        return id;
    }
    @Override
    public double getAmount() {
        return amount;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    public void setBankName(Bank bankName) {
        this.bankName = bankName;
    }
    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }
    public void setBankBranchCode(int bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }
    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setBankAccountKey(int bankAccountKey) {
        this.bankAccountKey = bankAccountKey;
    }
    public void setAmount(double amount) {
        this.amount = amount;

    }

    public String getHolderName() {
        return holderName;
    }
    public Bank getBankName() {
        return bankName; }
    public int getBankCode() {
        return bankCode;
    }
    public int getBankBranchCode() {
        return bankBranchCode; }
    public int getBankAccountNumber() {
        return bankAccountNumber;
    }
    public int getBankAccountKey() {
        return bankAccountKey;
    }
}
