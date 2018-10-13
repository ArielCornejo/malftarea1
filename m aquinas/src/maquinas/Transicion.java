/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinas;

/**
 *
 * @author felip
 */
public class Transicion {
    private String primera;
    private String union;
    private String segunda;

    public Transicion(String primera, String union, String segunda) {
        this.primera = primera;
        this.union = union;
        this.segunda = segunda;
    }

    public String getPrimera() {
        return primera;
    }

    public void setPrimera(String primera) {
        this.primera = primera;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getSegunda() {
        return segunda;
    }

    public void setSegunda(String segunda) {
        this.segunda = segunda;
    }
    
    
    
}
