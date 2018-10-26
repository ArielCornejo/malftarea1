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
     private ArrayList<Transicion> deltaAFD;
     private ArrayList<String> sigmaAFD;
     private ArrayList<ArrayList<String>> coneccionesDeCadaEstado;
     private String estadoDeInicio;
     private String estadoFinal;
     private String estadoInicioAFD;//estado de inicio del AFD
     private ArrayList<String> estadosFinales;//estado finales del AFD
     private ArrayList<String> estadosAFD;
     private ArrayList<ArrayList<String>> estados;
     private ArrayList<ArrayList<String>> estadosAuxiliares;
     private ArrayList<Transicion> deltaModificado;
     private ArrayList<String> coneccionesEstado;
     private ArrayList<String> nodos;
     private ArrayList<String> AuxiliaresEstados;
     
     
     HashMap<String,ArrayList<ArrayList<String>>> tabla;//una tabla para los estados, simula ser la tabla que se utiliza para los ejercicios
     private int contador;//similar al del AFND para dar numero a los estados 
     private ArrayList<Transicion> delta;
     
     

    public AFD(ArrayList<Transicion> d, ArrayList<String> s, ArrayList<String> e) {
        this.deltaAFD = new ArrayList <>();
        this.sigmaAFD = new ArrayList <>();
        this.coneccionesDeCadaEstado = new ArrayList <>();;
        this.estadosAFD = new ArrayList <>();
        this.estados = new ArrayList <>();
        this.nodos = new ArrayList <>();
        this.AuxiliaresEstados = new ArrayList <>();
        this.estadosAuxiliares = new ArrayList <>();
        tabla = new HashMap<>();
        this.deltaAFD = d;
        this.sigmaAFD = s;
        this.estadosAFD = e;
        this.estadoDeInicio = this.estadoSinEntrada();
        this.estadoFinal = this.estadoSinSalida();
        System.out.println(estadoDeInicio);
        System.out.println("ddd");
        /*this.imprimirdelta();
        nodosTodos();
        crearArrayListEstados();
        this.imprimirNodos();
        this.coneccionesDeCadaEstado();
        this.imprimirConeccionesDeCadaEstado();
        
        primeraIteracionConecciones();
        imprimirEstados();*/
        this.estadosFinales = new ArrayList<>();
        this.delta = new ArrayList<>();
        this.contador=0;
        this.afndAAfd();//metodo del ariel :3
        //ordenarEstados();
        //this.imprimirEstadosAuxiliares();
        
        
    }
    private void definirEstadosIniciales() {
        int conecciones = coneccionesEstado(this.estadoDeInicio);
        for (int i = 0; i < this.estados.size(); i++) {
            for (int j = 0; j < conecciones; j++) {
                for (int k = 0; k < this.deltaAFD.size(); k++) {
                    //if (this.deltaAFD.get(i).getPrimera().) {
                        
                    //}
                }
            }
        }
        //dfdfff
 
        
        
        ///for (int i = 0; i < this.deltaAFD.size(); i++) {
            //for (int j = 0; j < ; j++) {
               // if (this.deltaAFD.get(i).getUnion().equals(this.estados.get(j).get(0))) {
                    //this.
               // }
            //}
        //}
    }
    private void imprimirdelta(){
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            System.out.println(this.deltaAFD.get(i).getPrimera() + "," + this.deltaAFD.get(i).getUnion() + "," + this.deltaAFD.get(i).getSegunda());
        }
    }
    
    private void ordenarEstados(){
        int i = 0;
        int aux;
        while(i < this.coneccionesDeCadaEstado.size()){
            //System.out.println("puta la wea");
            if (i==0) {
                for (int j = 0; j < this.coneccionesDeCadaEstado.size(); j++) {
                    System.out.println(this.coneccionesDeCadaEstado.get(j));
                    if (this.coneccionesDeCadaEstado.get(j).get(0).equals(this.estadoDeInicio)) {
                        ArrayList<String> a = new ArrayList <>();
                        a = this.coneccionesDeCadaEstado.get(j);
                        System.out.println(this.coneccionesDeCadaEstado.get(j));
                        this.estadosAuxiliares.add(a);
                        System.out.println("aaaaa");
                        i++;
                    }
                }
            }
            else{
                for (int k = 0; k < this.estadosAuxiliares.get(i-1).size(); k++) {
                    System.out.println("1");
                    if (k+1 != 1 && !((k+1)%2==0) ) {
                        for (int j = 0; j < this.coneccionesDeCadaEstado.size(); j++) {
                            System.out.println("2");
                            if (this.estadosAuxiliares.get(i-1).get(k).equals(this.coneccionesDeCadaEstado.get(j).get(0))) {
                                int cont = 0;
                                for (int l = 0; l < this.estadosAuxiliares.size(); l++) {
                                    System.out.println("3");
                                    if (this.estadosAuxiliares.get(l).equals(this.coneccionesDeCadaEstado.get(j))) {
                                        System.out.println("aumento cont");
                                        cont++;
                                    }
                                }
                                if (cont == 0) {
                                    this.estadosAuxiliares.add(this.coneccionesDeCadaEstado.get(j));
                                    i++;
                                }
                                
                            }
                        }
                    }
                }
            }
        }
    } 
    
    private void imprimirEstadosAuxiliares(){
        for (int i = 0; i < this.estadosAuxiliares.size(); i++) {
            System.out.println(estadosAuxiliares.get(i));
        }
    }
    
    private void llenarestadosInicialesArryListEstados() {
        for (int i = 0; i < this.estados.size(); i++) {
            int  contador = 0 ;
            String s = null;
            for (int j = 0; j < this.deltaAFD.size(); j++) {
                boolean flag1 = false;
                if (this.deltaAFD.get(j).getUnion().equals(this.estados.get(i).get(0)) && !this.deltaAFD.get(j).getPrimera().equals(s) || this.deltaAFD.get(j).getUnion().equals(this.estados.get(i).get(0)) && contador == 0) {
                    this.estados.get(i).add(this.deltaAFD.get(j).getSegunda());
                    s = this.deltaAFD.get(j).getSegunda();
                    String aux;
                    int contadorinterior=0;
                    for (int k = 0; k < this.deltaAFD.size(); k++) {
                        if (contadorinterior == 0) {
                            if (this.deltaAFD.get(k).getUnion().equals("_") && this.deltaAFD.get(k).getPrimera().equals(s)) {
                                aux = this.deltaAFD.get(k).getSegunda();
                            }
                        }
                        
                    }
                    
                    
                    /*if (contador == 0) {
                        this.estados.get(i).add(this.deltaAFD.get(j).getSegunda());
                        contador ++;
                    }
                    else {
                        if (this.estados.get(i).get(contador).equals(this.deltaAFD.get(j).getPrimera()) && ) {
                            
                        }
                    }*/
                }
            }
        }
    }
    
    private void crearArrayListEstados() {
        for (int i = -1; i < this.deltaAFD.size(); i++) {
            if (i==-1) {
                ArrayList<String> ep = new ArrayList <>();
                ep.add("_");
                this.estados.add(ep);
            }
            else {
                int a = (int) this.deltaAFD.get(i).getUnion().charAt(0);
                if (a >= 65 && a<= 90 || a >= 97 && a<= 122 ) {
                    int contador =0;
                    for (int j = 0; j < estados.size(); j++) {
                        if (estados.get(j).get(0).equals(a)) {
                            contador ++;
                        }
                    }
                    if (contador == 0) {
                        ArrayList<String> nuevo = new ArrayList <>();
                        nuevo.add(this.deltaAFD.get(i).getUnion());
                        this.estados.add(nuevo);
                    }

                }
            }
        }
    }
    
    private void imprimirEstados() {
        for (int i = 0; i < this.estados.size(); i++) {
            System.out.println(estados.get(i));
        }
    }
    
    private int coneccionesEstado(String s){
        int conecciones = 0;
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            if (this.deltaAFD.get(i).getPrimera().equals(s)) {
                conecciones++;
            }
        }
        return conecciones;
        
    }
    
    private ArrayList<String> coneccionesEstadoArrayList( int conec, String s) {
        ArrayList<String> coneccionesEstadoAux = new ArrayList<>();
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            if (this.deltaAFD.get(i).getPrimera().equals(s)) {
                coneccionesEstadoAux.add(this.deltaAFD.get(i).getSegunda());
            }
        }
        return coneccionesEstadoAux;
    }
    
    private void coneccionesDeCadaEstado() {
        for (int i = 0; i < this.nodos.size(); i++) {
            ArrayList<String> aux = new ArrayList<>();
            aux.add(this.nodos.get(i));
            for (int j = 0; j < this.deltaAFD.size(); j++) {
                if (this.nodos.get(i).equals(this.deltaAFD.get(j).getPrimera())) {
                    //System.out.println(this.deltaAFD.get(i).getSegunda()+", "+ this.deltaAFD.get(j).getSegunda());
                    aux.add(this.deltaAFD.get(j).getUnion());
                    aux.add(this.deltaAFD.get(j).getSegunda());
                    
                }
            }
            this.coneccionesDeCadaEstado.add(aux);
        }
    }
    
    private void nodosTodos(){
        String primero;
        String segundo;
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            if (i==0) {
                primero = this.deltaAFD.get(i).getPrimera();
                segundo = this.deltaAFD.get(i).getSegunda();
                this.nodos.add(primero);
                this.nodos.add(segundo);
            }
            else {
                primero = this.deltaAFD.get(i).getPrimera();
                segundo = this.deltaAFD.get(i).getSegunda();
                int contadorPrimero = 0;
                int contadorSegundo = 0;
                for (int j = 0; j <this.nodos.size() ; j++) {
                    if (this.nodos.get(j).equals(primero)) {
                        contadorPrimero ++;
                    }
                }
                for (int j = 0; j < this.nodos.size(); j++) {
                    if (this.nodos.get(j).equals(segundo)) {
                        contadorSegundo ++;
                    }
                }
                if (contadorPrimero==0) {
                    this.nodos.add(primero);
                }
                if (contadorSegundo==0) {
                    this.nodos.add(segundo);
                }
            }           
        }
    }
    
    private void imprimirNodos(){
        for (int i = 0; i < this.nodos.size(); i++) {
            System.out.println(nodos.get(i));
        }
    }
    
    private void imprimirConeccionesDeCadaEstado(){
        for (int i = 0; i < coneccionesDeCadaEstado.size(); i++) {
            System.out.println(coneccionesDeCadaEstado.get(i));
        }
        System.out.println("aaaaaaaa mi pichulaaaaaaa");
        
        
    }
    
    private void primeraIteracionConecciones(){
        for (int x = 0; x < this.estados.size(); x++) {
            //System.out.println("entro al 1 for");
            for (int i = 0; i < coneccionesDeCadaEstado.size(); i++) {
                //System.out.println("entro al 2 for");
                for (int j = 0; j < coneccionesDeCadaEstado.get(i).size(); j++) {
                    //System.out.println("entro al 3 for");
                    if (coneccionesDeCadaEstado.get(i).size() > 1) {
                        if ((j+1)%2==0) {
                            if (this.estados.get(x).get(0).equals(this.coneccionesDeCadaEstado.get(i).get(j))) {
                                this.estados.get(x).add(this.coneccionesDeCadaEstado.get(i).get(j+1));
                                for (int k = 0; k < coneccionesDeCadaEstado.size(); k++) {
                                    //System.out.println("entro al 4 for");
                                    if (coneccionesDeCadaEstado.get(k).get(0).equals(this.coneccionesDeCadaEstado.get(i).get(j+1))) {
                                        for (int l = 0; l < coneccionesDeCadaEstado.get(k).size(); l++) {
                                            //System.out.println("entro al 5 for");
                                            if ((l+1)%2==0 && this.coneccionesDeCadaEstado.get(k).get(l+1).equals("_")) {
                                                this.estados.get(x).add(this.coneccionesDeCadaEstado.get(k).get(l+1));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    

  //No borrar estos metodos  
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
    //no borrar estos metodos
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
    
   
    //Ariel
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
}
