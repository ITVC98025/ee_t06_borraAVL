/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbalanceado;

/**
 *
 * @author ERIDE21
 */
public class Nodo {
    /*
    Clase creada con el fin de especificar los atributos de los objetos de típo Nodo
  */

	Nodo der;
	Nodo izq;
        Nodo padre;
	int dato;
        int fe;
        int peso;
	boolean root;

 
        public Nodo(int num, Nodo p) {
            dato = num;
            padre = p;
        }
        
	public Nodo(){
		this.izq = null;
		this.der = null;
                this.dato = 0;
                this.fe = 0;
	}
        
        public Nodo(int d){
		this.izq = null;
		this.der = null;
                this.dato = d;
                this.fe = 1;
                this.peso = 0;
	}


	public void setDato(int dato){
		this.dato = dato;
	}

	public int getDato(){
		return this.dato;
	}

	public void setDer(Nodo nodo){
		this.der = nodo;
	}

	public Nodo getDer(){
		return this.der;
	}

	public void setIzq(Nodo nodo){
		this.izq = nodo;
	}

	public Nodo getIzq(){
		return this.izq;
	}
}
