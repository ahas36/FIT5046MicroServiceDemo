/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.coaas.pizzaentity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ali
 */
@XmlRootElement
public class Pizza {

    private Long id;

    private String type;

    private Integer qty;

    private String stataus;

    public Pizza() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStataus() {
        return stataus;
    }

    public void setStataus(String stataus) {
        this.stataus = stataus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    
}
