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
public class Maquinas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = "(a.b)";
        ER er = new ER(s);
        AFND afnd = new AFND();
        afnd.crearAFND(er.getRpn());
        afnd.imprimirAFND();
        AFD afd = new AFD(afnd.getDelta(),afnd.getSigma(),afnd.getEstados());
    }
    
}
