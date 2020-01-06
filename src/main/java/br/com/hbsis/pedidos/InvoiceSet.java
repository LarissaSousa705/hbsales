package br.com.hbsis.pedidos;

public class InvoiceSet {
    private Integer amount;
    private String itemName;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "InvoiceSet{" +
                "amount=" + amount +
                ", itemName=" + itemName +
                '}';
    }
}
