package br.com.hbsis.pedidos;

import java.util.List;

public class InvoiceDTO {

    private String cnpjFornecedor;
    private String employeeUuid;
    private List<InvoiceSet> invoiceItemDTOSet;
    private Integer totalValue;

    public Integer getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
    }

    public List<InvoiceSet> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(List<InvoiceSet> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "cnpjFornecedor='" + cnpjFornecedor + '\'' +
                ", employeeUuid='" + employeeUuid + '\'' +
                ", invoiceItemDTOSet=" + invoiceItemDTOSet +
                ", totalValue=" + totalValue +
                '}';
    }
}
