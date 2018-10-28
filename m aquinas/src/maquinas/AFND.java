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
                //buscar al primer sin entrada, buscar al primer sin salida,excepcion en clausura de kleene
                
                //Bsucamos al primer sin salida
                boolean flag1=false;
                String primerSinEntrada = "";
                for(int j =this.delta.size()-1 ;j>=0 && !flag1;j--)
                {
                    Transicion objetivo = this.delta.get(j);
                    if(this.comprobarSinEntrada(objetivo.getPrimera()))
                    {
                       primerSinEntrada = objetivo.getPrimera();
                        System.out.println("sin Entrada:" + primerSinEntrada);
                       flag1=true;
                    }
                }
                boolean flag2 =false;
                String segundoSinSalida = "";
                String aux="";//parche para la clausura de kleen
                int contador=0;
                for(int j =this.delta.size()-1;j>=0 && !flag2;j--)
                {
                    Transicion objetivo = this.delta.get(j);
                    if(this.comprobarSinSalida(objetivo.getSegunda()))
                    {
                        if(contador==0)
                        {
                            contador++;
                            aux=objetivo.getSegunda();
                        }
                        else
                        {
                            if(!objetivo.getSegunda().equals(aux))
                            {
                                segundoSinSalida= objetivo.getSegunda();
                                System.out.println("2° sin salida: " + segundoSinSalida);
                                flag2=true;
                            }
                            
                        }
                    }
                }
                Transicion concatenacion = new Transicion(segundoSinSalida,"_",primerSinEntrada);
                this.delta.add(concatenacion);
                //
            }
            if(rpn.get(i).equals("|"))
            {
                //buscar a los dos primeros sin salida y sin entrada
              
               String primerSinEntrada = "";
               String segundoSinEntrada = "";
               boolean flag1=false;
               int contador=0;
               for(int j =this.delta.size()-1;j>=0 && !flag1;j--)
               {
                   Transicion objetivo = this.delta.get(j);
                   if(this.comprobarSinEntrada(objetivo.getPrimera()))
                   {
                       if(contador==0)
                       {
                          primerSinEntrada = objetivo.getPrimera();
                          contador++;
                       }
                       else
                       {
                           if(!primerSinEntrada.equals(objetivo.getPrimera())){
                                segundoSinEntrada = objetivo.getPrimera();
                                flag1=true;
                           }
                       }
                   }
               }
               //
                String primerSinSalida = "";
               String segundoSinSalida = "";
               boolean flag2=false;
               contador=0;
               for(int j =this.delta.size()-1;j>=0 && !flag2;j--)
               {
                   Transicion objetivo = this.delta.get(j);
                   if(this.comprobarSinSalida(objetivo.getSegunda()))
                   {
                       if(contador==0)
                       {
                          primerSinSalida = objetivo.getSegunda();
                          contador++;
                       }
                       else
                       {
                           if(!primerSinSalida.equals(objetivo.getSegunda())){
                                segundoSinSalida = objetivo.getSegunda();
                                flag2=true;
                           }
                       }
                   }
               }
               this.contador++;
               this.delta.add(new Transicion("q"+this.contador,"_",primerSinEntrada));
               this.delta.add(new Transicion("q"+this.contador,"_",segundoSinEntrada));
               this.estados.add("q"+this.contador);
               this.contador++;
               this.delta.add(new Transicion(primerSinSalida,"_","q"+this.contador));
               this.delta.add(new Transicion(segundoSinSalida,"_","q"+this.contador));
               this.estados.add("q"+this.contador);
               this.contador++;
               //
            }
            //Clausura de Kleen
            if(rpn.get(i).equals("*"))
            {
                //Codigo para la union
                if(rpn.get(i-1).equals("."))
                {
                    boolean flag1=false;
                    String primerSinEntrada = "";
                    for(int j =this.delta.size()-1 ;j>=0 && !flag1;j--)
                    {
                        Transicion objetivo = this.delta.get(j);
                        if(this.comprobarSinEntrada(objetivo.getPrimera()))
                        {
                           primerSinEntrada = objetivo.getPrimera();
                            System.out.println("sin Entrada:" + primerSinEntrada);
                           flag1=true;
                        }
                    }
                    boolean flag2=false;
                    String primerSinSalida = "";
                    for(int j =this.delta.size()-1 ;j>=0 && !flag2;j--)
                    {
                        Transicion objetivo = this.delta.get(j);
                        if(this.comprobarSinSalida(objetivo.getSegunda()))
                        {
                           primerSinSalida = objetivo.getSegunda();
                           System.out.println("sin Salida:" + primerSinSalida);
                           flag2=true;
                        }
                    }
                    Transicion loop = new Transicion(primerSinSalida,"_",primerSinEntrada);
                    this.delta.add(loop);
                    String comienzo = "q"+this.contador;
                    this.estados.add(comienzo);
                    this.contador++;
                    this.delta.add(new Transicion(comienzo,"_",primerSinEntrada));
                    this.delta.add(new Transicion(primerSinSalida,"_","q"+this.contador));
                    this.delta.add(new Transicion(comienzo,"_","q"+this.contador));
                    this.estados.add("q"+this.contador);
                    this.contador++;
                    
                    
                    
                    
                }
                //COdigo para la union
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
                int valorLetra = (int) rpn.get(i-1).charAt(0);
                if(valorLetra >= 65 && valorLetra<= 90 || valorLetra >= 97 && valorLetra<= 122)
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
    //Metodo para comprobar si el estado no tiene aristas entrantes
    public boolean comprobarSinEntrada(String estado)
    {
        for(int i=0;i<this.delta.size();i++)
        {
            if(this.delta.get(i).getSegunda().equals(estado))
            {
                return false;
            }
        }
        return true;
    }
    //Metodo para comprobar si el nodo no tiene aristas salientes
    public boolean comprobarSinSalida(String estado)
    {
        for(int i=0;i<this.delta.size();i++)
        {
            if(this.delta.get(i).getPrimera().equals(estado))
            {
                return false;
            }
        }
        return true;
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

    public ArrayList<Transicion> getDelta() {
        return delta;
    }

    public void setDelta(ArrayList<Transicion> delta) {
        this.delta = delta;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

}
