package org.prog3.prog3projetfinal.model;

public class MobileBankingAccount implements FinancialAccount {
    private String id;
    private String holderName;
    private MobileBankingService mobileBankingService;
    private int mobileNumber;
    private double amount;

    @Override
    public String getId() { return id; }
    @Override
    public double getAmount() { return amount; }

    public void setId(String id) { this.id = id; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public void setMobileBankingService(MobileBankingService mobileBankingService) { this.mobileBankingService = mobileBankingService; }
    public void setMobileNumber(int mobileNumber) { this.mobileNumber = mobileNumber; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getHolderName() { return holderName; }
    public MobileBankingService getMobileBankingService() { return mobileBankingService; }
    public int getMobileNumber() { return mobileNumber; }
}

