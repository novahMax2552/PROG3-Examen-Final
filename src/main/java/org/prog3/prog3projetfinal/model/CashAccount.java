package org.prog3.prog3projetfinal.model;

public class CashAccount implements FinancialAccount {
    private String id;
    private int amount;

    @Override
    public String getId() {
        return id;
     }
    @Override
    public double getAmount(){
        return amount;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}

