package arbolbalanceado;


import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Grafico extends JPanel{

    final ArbolB miArbol;
    HashMap posicion = null;
    HashMap tamArbol = null;
    boolean ocupado = true;
    int rootAhoja = 20, hojaAhoja = 30;
    final Dimension dimension = new Dimension(0,0);
    FontMetrics fm = null;

	public Grafico(ArbolB arbol){
          miArbol = arbol;
          setBackground(Color.white);
          posicion = new HashMap();
          tamArbol = new HashMap();
          ocupado = true;
          repaint();     
	}
        
         @Override
       public void paint(Graphics g){
             super.paint(g);
           //  g.drawString("total de nodos: "+miArbol.cantidad(), 450, 10);
            // g.drawString("altura del arbol : "+ miArbol.retornarAltura(), 450, 20);
             fm = g.getFontMetrics();

             if (ocupado) 
             {
               posiciones();
               ocupado = false;
             }

             Graphics2D g2d = (Graphics2D) g;
             g2d.translate(getWidth() / 2, rootAhoja);//pasa el objeto 2D a las coordenadas dadas (x,y)
             
             graficarArbol(g2d, miArbol.raiz, Integer.MAX_VALUE, Integer.MAX_VALUE, fm.getLeading() + fm.getAscent());
             
             fm = null;
       }
    
        private Dimension tamanoHojas(Nodo nodo){
          if (nodo == null) 
              return new Dimension(0,0);
 
          Dimension leftDim = tamanoHojas(nodo.izq);
          Dimension rightDim = tamanoHojas(nodo.der);
          
          int height = fm.getHeight() + rootAhoja + Math.max(leftDim.height, rightDim.height);
          int width = leftDim.width + hojaAhoja + rightDim.width;
          
          Dimension dimension2 = new Dimension(width, height);
          tamArbol.put(nodo, dimension2);
          
          return dimension2;
        }
    
        private void posicion(Nodo nodo, int izq, int der, int alto) {
          if (nodo == null) 
              return;

          Dimension ld = (Dimension) tamArbol.get(nodo.izq);
          if (ld == null) 
              ld = dimension;

          Dimension rd = (Dimension) tamArbol.get(nodo.der);
          if (rd == null) 
              rd = dimension;

          int center = 0;

          if (der != Integer.MAX_VALUE)
              center = der - rd.width - hojaAhoja/2;
          else if (izq != Integer.MAX_VALUE)
              center = izq + ld.width + hojaAhoja/2;

            String valor="";
            if(nodo.der==null && nodo.izq==null){
            valor=""+nodo.dato+":"+nodo.fe;
          }else{
            valor="" +nodo.dato+":"+nodo.fe;
          }

          int width = fm.stringWidth(valor);

          posicion.put(nodo,new Rectangle(center - width/2 - 3, alto, width + 6, fm.getHeight()));
         

          posicion(nodo.izq, Integer.MAX_VALUE, center - hojaAhoja/2, alto + fm.getHeight() + rootAhoja);
          posicion(nodo.der, center + hojaAhoja/2, Integer.MAX_VALUE, alto + fm.getHeight() + rootAhoja);
        }
    
        //Recibe el objeto grafico, el nodo, el tamano maximo 2 veces y la suma de las metricas de la fuente
        private void graficarArbol(Graphics2D g, Nodo nodo, int X, int Y, int Y2){
         if (nodo == null) 
             return;

         Rectangle rect = (Rectangle) posicion.get(nodo);
         
         g.draw(rect);
          String valor="";
          if(nodo.der==null && nodo.izq==null){
             valor=nodo.dato +":"+nodo.fe;
          }else{
            valor=nodo.dato+":"+nodo.fe;
          }

         g.drawString(valor, rect.x + 3, rect.y + Y2);
        


         if (X != Integer.MAX_VALUE){
            g.drawLine(X, Y, (int)(rect.x + rect.width/2), rect.y);
         }

            graficarArbol(g, nodo.izq, (int)(rect.x + rect.width/2), rect.y + rect.height, Y2);
            graficarArbol(g, nodo.der, (int)(rect.x + rect.width/2), rect.y + rect.height, Y2);

       }
   
        private void posiciones(){
         tamArbol.clear();
         posicion.clear();
         
         Nodo raiz = miArbol.raiz;
         if (raiz != null) {
             tamanoHojas(raiz);
             posicion(raiz, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
         }
        }
}