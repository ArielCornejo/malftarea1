/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinas;

import java.util.Scanner;

/**
 *
 * @author felip
 */
public class Maquinas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        String exp = leer.nextLine();
        String texto = leer.nextLine();
        ER er = new ER(exp);
        System.out.println("AFND:");
        AFND afnd = new AFND();
        afnd.crearAFND(er.getRpn());
        System.out.println("K="+afnd.getEstados());
        System.out.println("Sigma="+afnd.getSigma());
        afnd.imprimirAFND();
        System.out.println("s="+afnd.estadosSinEntrada().get(0));
        System.out.println("F="+afnd.estadosSinSalida());
        System.out.println(" ");
        System.out.println("AFD:");
        AFD afd = new AFD(afnd);
        System.out.println("K="+afd.getK());
        System.out.println("Sigma="+afd.getSigmaAFD());
        System.out.println("Delta:");
        afd.imprimirTransiciones();
        System.out.println("s="+afd.getEstadoInicioAFD());
        System.out.println("F="+afd.getEstadosFinales());
        System.out.println("  ");
        System.out.println("Ocurrencias:");
        Lector lector = new Lector(afd,texto);
    }
    
}
