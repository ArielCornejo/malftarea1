/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinas;

import java.util.ArrayList;

/**
 *
 * @author felip
 */
public class AFND {
    private ArrayList<String> estados;
    private ArrayList<String> sigma;
    private ArrayList<Transicion> delta;
    private int contador;
    //delta incoming
    public AFND() {
        this.estados = new ArrayList <>();
        this.sigma = new ArrayList <>();
        this.delta = new ArrayList<>();
        
        
    }

    public void crearAFND(ArrayList<String> rpn) {
        this.contador=0;
        for (int i = 0; i < rpn.size(); i++) {
            int a = (int) rpn.get(i).charAt(0);
            if (a >= 65 && a<= 90 || a >= 97 && a<= 122 ) {
                if(!this.sigma.contains(rpn.get(i))){
                    this.sigma.add(rpn.get(i));
                }
                this.crearTransicion(rpn.get(i));
                
            }
            else if(rpn.get(i).equals("."))
            {   
                int contadorEstadosLetra = 0;
                Transicion q0 = new Transicion(null,null,null);
                Transicion q1 = new Transicion(null,null,null);
                int j = this.delta.size()-1;
                while(j>=0) {
                    int b = (int) this.delta.get(j).getUnion().charAt(0);
                    if (b >= 65 && b<= 90 || b >= 97 && b<= 122 ) {
                        if (contadorEstadosLetra == 0) {
                            q0.setPrimera(this.delta.get(j).getPrimera());
                            q0.setSegunda(this.delta.get(j).getSegunda());
                            q0.setUnion(this.delta.get(j).getUnion());
                            System.out.println("primero ");
                            System.out.println(q0.getPrimera());
                            contadorEstadosLetra ++;
                        }
                        else {
                            if (contadorEstadosLetra == 1) {
                                q1.setPrimera(this.delta.get(j).getPrimera());
                                q1.setSegunda(this.delta.get(j).getSegunda());
                                q1.setUnion(this.delta.get(j).getUnion());
                                System.out.println("segundo ");
                                System.out.println(q1.getSegunda());
                                contadorEstadosLetra = -1;
                            }
                        }
                        
                    }
                    j=j-1;
                    
                }
                Transicion ep = new Transicion(q1.getSegunda(),"_",q0.getPrimera());
                this.delta.add(ep);
            }       
        }
        
    }
    
    public void imprimirAFND() {
        for (int i = 0; i < this.delta.size(); i++) {
            System.out.println(this.delta.get(i).getPrimera() + "," + this.delta.get(i).getUnion() + "," + this.delta.get(i).getSegunda());
        }
    
    }
    
    public void crearTransicion(String letra)
    {
        String inicio = "q"+this.contador;
        this.contador++;
        String finalo = "q"+this.contador;
        Transicion nueva = new Transicion(inicio,letra,finalo);
        this.delta.add(nueva);
        this.contador++;
    }
    
    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }

    public ArrayList<String> getSigma() {
        return sigma;
    }

    public void setSigma(ArrayList<String> sigma) {
        this.sigma = sigma;
    }
    
    
    
}
