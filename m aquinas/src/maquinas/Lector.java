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
public class Lector {
    private AFD afd;
    private int cont = 0;
    private int Inicio = -1;
    private int Final = -1;
    private boolean automataActivo = false;
    private boolean finalo = false;
    private String estadoInicial;
    private String posibleSumidero;
    private String caracter;
    private String estadoActual;
    private String SiguienteEstado;
    private String texto;
    private ArrayList<Transicion> transiciones;
    private ArrayList<String> estadosFinales;

    public Lector(AFD afd,String s) {
        this.afd = afd;
        this.texto = s+"&&";
        this.transiciones = new ArrayList <>();
        this.estadoInicial = afd.getEstadoInicioAFD();
        this.estadosFinales = new ArrayList <>();
        this.estadoActual = afd.getEstadoInicioAFD();
        this.SiguienteEstado = "";
        this.leer();
    }

    private void leer() { 
        //busca posibles sumideros
        for (int i = 0; i < this.afd.getTabla().get("estados").size(); i++) {
            ArrayList<String> estadosIniciales = this.afd.getTabla().get("estados").get(i);
            for (int j = 0; j < estadosIniciales.size(); j++) {
                if (estadosIniciales.get(j).equals("sumidero")) {
                    this.posibleSumidero= "Q" + i;
                }
            }
        }
        //agrega solo las transiciones que no tengan algun tipo de union con el sumidero
        for (Transicion transicion : this.afd.getDelta()) {
            if (!transicion.getPrimera().equals(this.posibleSumidero) && !transicion.getSegunda().equals(this.posibleSumidero)) {
               this.transiciones.add(transicion);
            }
        }
        //agrega solo los estados finales que sean sumideros
        for (String estadof : this.afd.getEstadosFinales()) {
            if (estadof != this.posibleSumidero) {
                this.estadosFinales.add(estadof);
            }
        }
        //busca las ocurrencias dado el texto ingresado
        for (int i = 0; i < texto.length(); i++) {
            this.cont=0;
            this.caracter = Character.toString(this.texto.charAt(i));
            for (Transicion transicion : this.transiciones) {
                if (transicion.getPrimera().equals(this.estadoActual) && transicion.getUnion().equals(this.caracter)) {
                   this.SiguienteEstado = transicion.getSegunda();
                   this.cont=1;
                }
            }
            if (this.cont == 0) {
                this.SiguienteEstado = null;
            }
            if (this.SiguienteEstado == null) { 
                if (this.automataActivo == true) { 
                    i--;
                }
                if (this.Inicio!=-1 && this.Final!=-1){ 
                    System.out.println(this.Inicio+" "+this.Final);
                }
                this.Inicio = -1; 
                this.Final = -1;
                this.automataActivo = false;
                this.estadoActual = this.afd.getEstadoInicioAFD();
            } 
            else { 
                if (this.automataActivo == false) { 
                    this.Inicio = i;
                    for (String estadof : this.estadosFinales) {
                        if (estadof == this.SiguienteEstado) {
                            this.finalo = true;
                        }
                    }
                    if (this.finalo == true){
                        this.Final =i;
                    }
                    this.finalo = false;
                }
                else {
                    for (String estadof : this.estadosFinales) {
                        if (estadof == this.SiguienteEstado) {
                            this.finalo = true;
                        }
                    }
                    if (this.finalo == true){
                        this.Final =i;
                    }
                    this.finalo = false;
                }
                this.automataActivo = true;
                this.estadoActual = this.SiguienteEstado;
            }
        }
    }

    public AFD getAfd() {
        return afd;
    }

    public void setAfd(AFD afd) {
        this.afd = afd;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public int getInicio() {
        return Inicio;
    }

    public void setInicio(int Inicio) {
        this.Inicio = Inicio;
    }

    public int getFinal() {
        return Final;
    }

    public void setFinal(int Final) {
        this.Final = Final;
    }

    public boolean isAutomataActivo() {
        return automataActivo;
    }

    public void setAutomataActivo(boolean automataActivo) {
        this.automataActivo = automataActivo;
    }

    public boolean isFinalo() {
        return finalo;
    }

    public void setFinalo(boolean finalo) {
        this.finalo = finalo;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public String getPosibleSumidero() {
        return posibleSumidero;
    }

    public void setPosibleSumidero(String posibleSumidero) {
        this.posibleSumidero = posibleSumidero;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getSiguienteEstado() {
        return SiguienteEstado;
    }

    public void setSiguienteEstado(String SiguienteEstado) {
        this.SiguienteEstado = SiguienteEstado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    public ArrayList<String> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(ArrayList<String> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }
    
}
