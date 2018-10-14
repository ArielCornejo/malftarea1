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
            if(rpn.get(i).equals("|"))
            {
                System.out.println("entre a la union");
               ArrayList<String> inicios = this.estadosSinEntrada();
               for (int j = 0; j < inicios.size(); j++)
               {
                  Transicion nueva = new Transicion("q"+this.contador,"_",inicios.get(j));
                  this.delta.add(nueva);
                  
                  
               }
               this.estados.add("q"+this.contador);
               this.contador++;
               ArrayList<String> finales = this.estadosSinSalida();
               for (int j = 0; j < inicios.size(); j++)
               {
                  Transicion nueva = new Transicion(finales.get(j),"_","q"+this.contador);
                  this.delta.add(nueva);
                  
                  
               }
               this.estados.add("q"+this.contador);
               this.contador++;
            }
            if(rpn.get(i).equals("*"))
            {
                if(rpn.get(i-1).equals("."))
                {
                    
                }
                if(rpn.get(i-1).equals("|"))
                {
                    String sinEntrada = this.estadosSinEntrada().get(0);
                    String sinSalida = this.estadosSinSalida().get(0);
                    Transicion loop = new Transicion(sinSalida,"_",sinEntrada);
                    this.delta.add(loop);
                    String comienzo = "q"+this.contador;
                    this.estados.add(comienzo);
                    this.contador++;
                    this.delta.add(new Transicion(comienzo,"_",sinEntrada));
                    this.delta.add(new Transicion(sinSalida,"_","q"+this.contador));
                    this.delta.add(new Transicion(comienzo,"_","q"+this.contador));
                    this.estados.add("q"+this.contador);
                    this.contador++;
                }
                if(!rpn.get(i-1).equals(".") || !rpn.get(i-1).equals("|"))
                {
                    Transicion anterior = this.delta.get(delta.size()-1);
                    this.delta.add(new Transicion(anterior.getSegunda(),"_",anterior.getPrimera()));
                    String comienzo = "q"+this.contador;
                    this.contador++;
                    this.estados.add(comienzo);
                    this.delta.add(new Transicion(comienzo,"_",anterior.getPrimera()));
                    String finalo = "q"+this.contador;
                    this.contador++;
                    this.estados.add(finalo);
                    this.delta.add(new Transicion(anterior.getSegunda(),"_",finalo));
                    this.contador++;
                    this.delta.add(new Transicion(comienzo,"_",finalo));
                }
                
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
        this.estados.add(inicio);
        this.contador++;
        String finalo = "q"+this.contador;
        this.estados.add(finalo);
        Transicion nueva = new Transicion(inicio,letra,finalo);
        this.delta.add(nueva);
        this.contador++;
    }
    
    public ArrayList<String> estadosSinEntrada()
    {
        ArrayList<String> nuevo = new ArrayList<String>();
        for(int i=0;i<this.estados.size();i++)
        {
            String estado = this.estados.get(i);
            int apariciones=0;
            for(int j =0;j<this.delta.size() && apariciones==0;j++)
            {
               if(estado.equals(this.delta.get(j).getSegunda()))
               {
                   apariciones++;
               }
            }
            if(apariciones==0)
            {
                nuevo.add(estado);
            }
        }
        return nuevo;
     }
    
    public ArrayList<String> estadosSinSalida()
    {
         ArrayList<String> nuevo = new ArrayList<String>();
        for(int i=0;i<this.estados.size();i++)
        {
            String estado = this.estados.get(i);
            int apariciones=0;
            for(int j =0;j<this.delta.size() && apariciones==0;j++)
            {
               if(estado.equals(this.delta.get(j).getPrimera()))
               {
                   apariciones++;
               }
            }
            if(apariciones==0)
            {
                nuevo.add(estado);
            }
        }
        return nuevo;
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
