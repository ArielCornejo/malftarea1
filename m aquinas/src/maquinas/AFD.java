/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author felip
 */
public class AFD {
     private AFND afnd;
     private ArrayList<Transicion> deltaAFD;
     private ArrayList<String> sigmaAFD;
     private String estadoDeInicio;
     private String estadoFinal;
     private String estadoInicioAFD;//estado de inicio del AFD
     private ArrayList<String> estadosFinales;//estado finales del AFD
     private ArrayList<String> estadosAFD;
     HashMap<String,ArrayList<ArrayList<String>>> tabla;//una tabla para los estados, simula ser la tabla que se utiliza para los ejercicios
     private int contador;//similar al del AFND para dar numero a los estados 
     private ArrayList<Transicion> delta;

    public AFD(AFND afnd) {
        this.afnd = afnd;
        this.deltaAFD = new ArrayList <>();
        this.sigmaAFD = new ArrayList <>();
        this.estadosAFD = new ArrayList <>();
        tabla = new HashMap<>();
        this.deltaAFD = afnd.getDelta();
        this.sigmaAFD = afnd.getSigma();
        this.estadosAFD = afnd.getEstados();
        this.estadoDeInicio = this.estadoSinEntrada();
        this.estadoFinal = this.estadoSinSalida();
        this.estadosFinales = new ArrayList<>();
        this.delta = new ArrayList<>();
        this.contador=0;
        this.afndAAfd();
    }
    
    private String estadoSinEntrada() {
        String nuevo = null;
        for(int i=0;i<this.estadosAFD.size();i++){
            String estado = this.estadosAFD.get(i);
            int apariciones=0;
            for(int j =0;j<this.deltaAFD.size() && apariciones==0;j++){
               if(estado.equals(this.deltaAFD.get(j).getSegunda())){
                   apariciones++;
               }
            }
            if(apariciones==0){
                nuevo = estado;
            }
        }
        return nuevo;
     }
    
    private String estadoSinSalida() {
        String nuevo = null;
        for(int i=0;i<this.estadosAFD.size();i++){
            String estado = this.estadosAFD.get(i);
            int apariciones=0;
            for(int j =0;j<this.deltaAFD.size() && apariciones==0;j++){
               if(estado.equals(this.deltaAFD.get(j).getPrimera())){
                   apariciones++;
               }
            }
            if(apariciones==0){
                nuevo = estado;
            }
        }
        return nuevo;
     }
   //Ariel
    /*Param armar el la tabla primero se agregan los estados que por epsilon salen desde el inicio,
    luego se agregan cada uno de los que de este salen por los simbolos del alfabeto y al final de cada 
    arreglo de estado se agrega un identificiador de la manera "Q(numero)" que representa que estado es
    en el AFD. Una vez que se logra eso se agrgan las transiciones.
    */
    public void afndAAfd()
    {
        //se arma la tabla,la primera key son los estados las que sigan son las letras del alfabeto
        tabla.put("estados", new ArrayList<ArrayList<String>>());
        for(int i =0;i<this.sigmaAFD.size();i++)
        {
            tabla.put(this.sigmaAFD.get(i),new ArrayList<ArrayList<String>>());
        }
        System.out.println("estado de inicio" + this.estadoDeInicio);
        //rellenar estado inicial
        tabla.get("estados").add(new ArrayList<String>());
        tabla.get("estados").get(0).add(estadoDeInicio);
        for (int i = 0; i < tabla.get("estados").get(0).size(); i++)
        {
            ArrayList<String> proximos =this.encontrarAdyacencias(tabla.get("estados").get(0).get(i), "_");
            for (int j = 0; j < proximos.size(); j++) 
            {
                tabla.get("estados").get(0).add(proximos.get(j));
            }
            
        }
        tabla.get("estados").get(0).add("Q"+ this.contador);
        this.contador++;
        //Automatizacion
        for (int i = 0; i < tabla.get("estados").size(); i++)
        {
            ArrayList<String> estadoAFD = tabla.get("estados").get(i);//estado que se esta revisando
            for (int j = 0; j < this.sigmaAFD.size(); j++) {
                String letra = this.sigmaAFD.get(j);
                this.tabla.get(letra).add(new ArrayList<String>());//añadir  un nuevo estado
                for (int k = 0; k < estadoAFD.size(); k++) {
                    ArrayList<String> proximos = this.encontrarAdyacencias(estadoAFD.get(k),
                                                                           letra);
                    for (int l = 0; l < proximos.size(); l++) {
                        //se pueden repetir estados
                       tabla.get(letra).get(i).add(proximos.get(l));
                    }
                }
                for (int k = 0; k < tabla.get(letra).get(i).size(); k++) {
                    ArrayList<String> proximos = this.encontrarAdyacencias(tabla.get(letra).get(i).get(k),
                                                                           "_");
                    for (int l = 0; l < proximos.size(); l++) {
                       tabla.get(letra).get(i).add(proximos.get(l));
                    }
                }
                if(this.tabla.get(letra).get(i).isEmpty())
                {
                    this.tabla.get(letra).get(i).add("sumidero");
                }
                //comprobar que el estado no esta presente para agregarlo
                if(this.encontrarEstado(this.tabla.get(letra).get(i))==null)
                {
                    tabla.get(letra).get(i).add("Q" + this.contador);
                    this.contador++;
                    tabla.get("estados").add(tabla.get(letra).get(i));//añadir el estado si es que no esta previamente
                }
                else
                {
                    tabla.get(letra).get(i).add(this.encontrarEstado(this.tabla.get(letra).get(i)));
                }
                //formar transiciones
                int posicion = tabla.get(letra).get(i).size()-1;
                this.delta.add(new Transicion(estadoAFD.get(estadoAFD.size()-1),letra,tabla.get(letra).get(i).get(posicion)));
                
            }
        }
        //
        //imprimir primeros estados
        System.out.println("-------------");
        System.out.println("empezo");
        System.out.println("tamaño tabla" + tabla.get("estados").size());
        System.out.println("Estados generados");
        for (int i = 0; i < tabla.get("estados").size(); i++) {
            System.out.printf("Q" + i + ": ");
            ArrayList<String> estadosIniciales = tabla.get("estados").get(i);
            for (int j = 0; j < estadosIniciales.size(); j++) {
                System.out.printf(estadosIniciales.get(j) + ",");
            }
            System.out.printf("\n");
        }
        
        //formar transiciones
        for (int i = 0; i < this.delta.size(); i++) {
            System.out.println(this.delta.get(i).getPrimera() +","+this.delta.get(i).getUnion()+","+this.delta.get(i).getSegunda());
        }
        //Agregar estados de inicio y de fin del AFD
        ArrayList<ArrayList<String>> estados = this.tabla.get("estados");
        for (int i = 0; i < estados.size(); i++) {
            ArrayList<String> estado = estados.get(i);
            if(estado.contains(this.estadoDeInicio))
            {
                this.estadoInicioAFD = estado.get(estado.size()-1);
            }
            if(estado.contains(this.estadoFinal))
            {
                this.estadosFinales.add(estado.get(estado.size()-1));
            }
        }
        System.out.println("Estado Inicio " + this.estadoInicioAFD);
        System.out.println("Estados Finales");
        for (int i = 0; i < this.estadosFinales.size(); i++) {
            System.out.println("Estado " + this.estadosFinales.get(i));
        }
    }
    /*funcion para buscar los estados adyacentes a un estado al usar un dterminda simbolo del 
    alfabeto como transicion*/
    private ArrayList<String> encontrarAdyacencias(String estado,String caracter)
    {
        ArrayList<String> estadosAdyacentes = new ArrayList<>();
        for(int i =0;i<this.deltaAFD.size();i++)
        {
            Transicion objetivo = this.deltaAFD.get(i);
            if(objetivo.getPrimera().equals(estado) && objetivo.getUnion().equals(caracter))
            {
                estadosAdyacentes.add(objetivo.getSegunda());
            }
        }
        return estadosAdyacentes;
    }
    //funcion para buscar si el estado ya fue agregado
    private boolean encontrarCoincidencia(ArrayList<String> nuevo)
    {
        boolean encontrado = false;
        ArrayList<ArrayList<String>> estadosA= this.tabla.get("estados");
        if(nuevo.isEmpty())
        {
            for (int i = 0; i < estadosA.size(); i++) {
                if(estadosA.get(i).isEmpty())
                {
                    return true;
                }
            }
        }
        for (int i = 0; i < estadosA.size() && !encontrado; i++) {
            ArrayList<String> objetivo = estadosA.get(i);
            int aux=0;
            for (int j = 0; j < objetivo.size(); j++) {
                if(nuevo.contains(objetivo.get(j)))
                {
                    aux++;
                }
            }
            if(aux==(nuevo.size()))
            {
                
                return true;
            }
        }
        return encontrado;
    }
    //funcion para comprobar si el estado ya fue agregado e identificarlo
    private String encontrarEstado(ArrayList<String> nuevo)
    {
        ArrayList<ArrayList<String>> estadosA= tabla.get("estados");
        for (int i = 0; i < estadosA.size(); i++) {
            ArrayList<String> objetivo = estadosA.get(i);
            int aux=0;
            for (int j = 0; j < objetivo.size()-1; j++) {
                if(nuevo.contains(objetivo.get(j)))
                {
                    aux++;
                }
            }
            if(aux==(nuevo.size()))
            {
                
                return objetivo.get(objetivo.size()-1);
            }
            
            
        }
        return null;
    }

    public ArrayList<String> getSigmaAFD() {
        return sigmaAFD;
    }

    public void setSigmaAFD(ArrayList<String> sigmaAFD) {
        this.sigmaAFD = sigmaAFD;
    }

    public ArrayList<Transicion> getDeltaAFD() {
        return deltaAFD;
    }

    public void setDeltaAFD(ArrayList<Transicion> deltaAFD) {
        this.deltaAFD = deltaAFD;
    }

    public ArrayList<Transicion> getDelta() {
        return delta;
    }

    public void setDelta(ArrayList<Transicion> delta) {
        this.delta = delta;
    }

    public String getEstadoInicioAFD() {
        return estadoInicioAFD;
    }

    public void setEstadoInicioAFD(String estadoInicioAFD) {
        this.estadoInicioAFD = estadoInicioAFD;
    }

    public ArrayList<String> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(ArrayList<String> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<String, ArrayList<ArrayList<String>>> tabla) {
        this.tabla = tabla;
    }

}
