/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
/**
 *
 * @author felip
 */
public class ER {
    private String er;
    private String[] erCadena;
    private ArrayList<String> rpn;
    private HashMap<String,Integer> precedencias;
    
    public ER(String er) {
        this.er = er;
        this.rpn = new ArrayList<>();
        this.precedencias = new HashMap<>();
        this.separarString();
        this.llenarPrecedencias();
        this.aplicarShuntingYard();
        this.imprimirRpn();
    }
    
    
    public void separarString() {
        erCadena = new String [er.length()];
        erCadena = er.split("");
    }
    
    public void llenarPrecedencias() {
        this.precedencias.put("(", 1);
        this.precedencias.put("|", 2);
        this.precedencias.put(".", 3);
        this.precedencias.put("*", 4);
        this.precedencias.put("_", 6);
        this.precedencias.put("~", 6);
        //"a|(a.b)"
    }

    public void aplicarShuntingYard() {
        Stack<String> pila = new Stack<String>();
        
        for (int i = 0; i < erCadena.length; i++) {
            int a = (int) erCadena[i].charAt(0);
            if (a >= 65 && a<= 90 || a >= 97 && a<= 122 ) {
                this.rpn.add(erCadena[i]);
            }
            /*if(erCadena[i].equals("("))
            {
                pila.push(erCadena[i]);
            }*/
            else {
                /*if (pila.size() == 0) {
                    pila.push(erCadena[i]);
                }*/
                if(erCadena[i].equals("(") || pila.size() ==0)
                {
                    pila.push(erCadena[i]);
                }
               
                
                else {
                    String b = ")";
                    if(erCadena[i].equals(b)){
                        String c = "(";
                        while(!pila.peek().equals(c)){
                            this.rpn.add(pila.pop());
                        }
                        pila.pop();
                        //System.out.println(this.precedencias.get(pila.peek()));
                        //System.out.println(this.precedencias.get(erCadena[i]));
                    }
                    else{
                        boolean flag = true;
                        while(flag && pila.size()>0 ){
                            if (this.precedencias.get(pila.peek()) < this.precedencias.get(erCadena[i]) /*|| this.precedencias.get(pila.peek()) > this.precedencias.get(erCadena[i])*/) {
                                pila.push(erCadena[i]);
                                flag = false;
                            }
                            else {
                                if (this.precedencias.get(pila.peek()) >= this.precedencias.get(erCadena[i])) {
                                    this.rpn.add(pila.pop());
                                    //pila.push(erCadena[i]);
                                }
                            }
                        }
                        if(pila.size()==0)
                        {
                            pila.push(erCadena[i]);
                        }
                    }
                    
                    
                }
            }
        }
        while (pila.size() >0) {
            this.rpn.add(pila.pop());
        }

    }
    
    public void imprimirRpn () {
        for (int i = 0; i < this.rpn.size(); i++) {
            System.out.println(this.rpn.get(i));
        }
    }



    public String getEr() {
        return er;
    }

    public void setEr(String er) {
        this.er = er;
    }

    public String[] getErCadena() {
        return erCadena;
    }

    public void setErCadena(String[] erCadena) {
        this.erCadena = erCadena;
    }

    public ArrayList<String> getRpn() {
        return rpn;
    }

    public void setRpn(ArrayList<String> rpn) {
        this.rpn = rpn;
    }
    
    
    
    
}
