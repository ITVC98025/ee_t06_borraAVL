package arbolbalanceado;

import static arbolbalanceado.ArbolBalanceado.ar;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ArbolB {

    Nodo raiz;
    int cant;
    int altura;

    public ArbolB() {
        raiz = null;
    }

    public boolean insertar(int nuevo) {
        if (raiz == null)
            raiz = new Nodo(nuevo, null);
        else {
            Nodo aux = raiz;
            Nodo padre;
            while (true) {
                if (aux.dato == nuevo)
                    return false;
 
                padre = aux;

                boolean irIzq = aux.dato > nuevo;
                aux = irIzq ? aux.izq : aux.der; //condicion  ? verdadero : falso
                
                if (aux == null) {
                    if (irIzq) {
                        padre.izq = new Nodo(nuevo, padre);
                    } else {
                        padre.der = new Nodo(nuevo, padre);
                    }
                    rebalanceo(padre);
                    break;
                }
            }
        }
        return true;
    }
 
    public void eliminar(int dato) {
        if (raiz == null)
            return;
        Nodo aux = raiz;
        Nodo padre = raiz;
        Nodo delNodo = null;
        Nodo child = raiz;
 
        while (child != null) {
            padre = aux;
            aux = child;
            child = dato >= aux.dato ? aux.der : aux.izq;// condicion  ? verdadero : falso

            if (dato == aux.dato)
                delNodo = aux;
        }
 
        if (delNodo != null) {
            delNodo.dato = aux.dato;
            child = aux.izq != null ? aux.izq : aux.der;// condicion  ? verdadero : falso
 
            if (raiz.dato == dato) {
                raiz = child;
            } else {
                if (padre.izq == aux) {
                    padre.izq = child;
                } else {
                    padre.der = child;
                }
                rebalanceo(padre);
            }
        }
    }
 
    private void rebalanceo(Nodo reba) {
        recibirBalanceo(reba);
 
        if (reba.fe == -2) {
            if (obtenerPeso(reba.izq.izq) >= obtenerPeso(reba.izq.der))
                reba = rotacionIzq(reba);
            else
                reba = rotaCompID(reba);
 
        } else if (reba.fe == 2) {
            if (obtenerPeso(reba.der.der) >= obtenerPeso(reba.der.izq))
                reba = rotacionDer(reba);
            else
                reba = rotaCompDI(reba);
        }
 
        if (reba.padre != null) {
            rebalanceo(reba.padre);
        } else {
            raiz = reba;
        }
    }
    //Rotacion simple a la izquierda
    private Nodo rotacionDer(Nodo rotaIzq) {
        System.out.println("Rotacion simple a la derecha");
        Nodo aux = rotaIzq.der;
        aux.padre = rotaIzq.padre;
 
        rotaIzq.der = aux.izq;
 
        if (rotaIzq.der != null)
            rotaIzq.der.padre = rotaIzq;
 
        aux.izq = rotaIzq;
        rotaIzq.padre = aux;
 
        if (aux.padre != null) {
            if (aux.padre.der == rotaIzq) {
                aux.padre.der = aux;
            } else {
                aux.padre.izq = aux;
            }
        }
 
        recibirBalanceo(rotaIzq, aux);
 
        return aux;
    }
    //Rotacion simple a la derecha
    private Nodo rotacionIzq(Nodo rotaDer) {
         System.out.println("Rotacion simple a la izquierda");

        Nodo temp = rotaDer.izq;
        temp.padre = rotaDer.padre;
 
        rotaDer.izq = temp.der;
 
        if (rotaDer.izq != null)
            rotaDer.izq.padre = rotaDer;
 
        temp.der = rotaDer;
        rotaDer.padre = temp;
 
        if (temp.padre != null) {
            if (temp.padre.der == rotaDer) {
                temp.padre.der = temp;
            } else {
                temp.padre.izq = temp;
            }
        }
 
        recibirBalanceo(rotaDer, temp);
 
        return temp;
    }
 
    private Nodo rotaCompID(Nodo rotaCompID) {
        System.out.println("Rotacion compuesta izquierda a derecha");
        rotaCompID.izq = rotacionDer(rotaCompID.izq);
        return rotacionIzq(rotaCompID);
    }
 
    private Nodo rotaCompDI(Nodo rotaCompDI) {
        System.out.println("Rotacion compuesta derecha a izquierda");
        rotaCompDI.der = rotacionIzq(rotaCompDI.der);
        return rotacionDer(rotaCompDI);
    }
 
    private int obtenerPeso(Nodo aux) {
        if (aux == null)
            return -1;
        return 1 + Math.max(obtenerPeso(aux.der), obtenerPeso(aux.izq));
    }
 
    private void recibirBalanceo(Nodo... nodes) {
        for (Nodo aux : nodes)
            aux.fe = obtenerPeso(aux.der) - obtenerPeso(aux.izq);
    }
    
    public JPanel dibujaArbol(ArbolB ar) {
        return new Grafico(ar);
    }

    public void dibujarArbol() {
        JFrame arbolB = new JFrame("Arbol grafico");
        arbolB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        arbolB.add(dibujaArbol(ar));
        arbolB.setSize(600, 800);
        arbolB.setVisible(true);
    }

    public void muestraMenuAVL() {
        /*
         Método encargado de desplegar el menu de opciones disponibles
         */
        System.out.println("");
        System.out.println("1.- Insertar elemento en el arbol");
        System.out.println("2.- Buscar elemento en el arbol");
        System.out.println("3- Remover elemento del arbol");
        System.out.println("4.- Recorrido PreOrder");
        System.out.println("5.- Recorrido InOrder");
        System.out.println("6.- Representacion del arbol");
        System.out.println("7.- Salir del programa");
        menuOpcionAVL();
    }

    public void menuOpcionAVL() {
        /*
         Método encargado de optener la opción del usuario
         y ejecutar posteriormente la instruccion ordenada
         */

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Opcion invalida, introduzca solo opciones aceptables");
            muestraMenuAVL();
        }

        int info;
        boolean resultado;
        switch (opcion) {
            case 1:
                info = consigueValor();
                insertar(info);
                muestraMenuAVL();
                break;
            case 2:
                try {
                    info = consigueValor();
                    resultado = buscaElemento(info);
                    if (resultado) {
                        System.out.println("El elemento se encuentra en el arbol");
                    } else {
                        System.out.println("El elemento no se encuentra en el arbol");
                    }
                    muestraMenuAVL();
                } catch (Exception e) {

                }
                break;
            case 3:
                info = consigueValor();
                eliminar(info);
                muestraMenuAVL();
                break;
            case 4:
                preOrder(raiz);
                System.out.println();
                muestraMenuAVL();
                break;
            case 5:
                inOrder(raiz);
                System.out.println();
                muestraMenuAVL();
                break;
            case 6:
                dibujarArbol();
                muestraMenuAVL();
                break;
            case 7:
                break;
            default:
                System.out.println("Opcion invalida");
                menuOpcionAVL();
                break;
        }

    }

    public int consigueValor() {

             //Método encargado de verificar que los valores que introduce el usuario
        //sean valores aceptables
        System.out.print("\nvalor: ");
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        try {
            opcion = sc.nextInt();
            return opcion;
        } catch (Exception e) {
            System.out.println("Opcion invalida, introduzca solo valores enteros");
            return consigueValor();
        }

    }

    public void preOrder(Nodo reco) {

          //Método para recorrer el árbol en el sentido preOrder(raíz, izquierda, derecha)
        if (reco != null) {
            System.out.print(reco.dato + " ");
            preOrder(reco.izq);
            preOrder(reco.der);
        }
    }

    public void inOrder(Nodo reco) {

          //Método para recorrer el árbol en el sentido inOrder(izquierda, raíz, derecha)
        if (reco != null) {
            inOrder(reco.izq);
            System.out.print(reco.dato + " ");
            inOrder(reco.der);
        }
    }

    public boolean buscaElemento(int info) {

        //Método encargado de proporcionar la información del nodo solicitado(nivel del nodo, valor de sus nodos
        //, tipo de nodo(rama, hoja)
        //)
        
      	Nodo anterior = null, reco;
        int nivel = 0;
        reco = raiz;
        while (reco != null) {
            nivel++;
            anterior = reco;
            if (info == reco.dato) {
                if (reco.izq != null)
                {
                    if (reco.der != null) //System.out.println(reco.der.getDato());
                    {
                        if (reco.der == null && reco.izq == null) //System.out.println("nodo de tipo: hoja");
                        
                        {
                            return true;
                        }
                    }
                }
            }
            if (info < reco.dato) {
                reco = reco.izq;
            } else {
                reco = reco.der;
            }

        }
        return false;
    }
}