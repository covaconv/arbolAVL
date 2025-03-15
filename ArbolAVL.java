/*
 * Filename: c:\Users\rodri\OneDrive\Escritorio\ITAM\3er semestre\edd_avanzadas\src\main\java\arboles\ArbolAVL.java
 * Path: c:\Users\rodri\OneDrive\Escritorio\ITAM\3er semestre\edd_avanzadas\src\main\java\arboles
 * Created Date: Monday, February 24th 2025, 11:45:46 am
 * Author: covaconv
 * 
 * Copyright (c) 2025 Yomero
 */

package arboles;

import java.util.ArrayList;
import java.util.Iterator;

public class ArbolAVL<T extends Comparable<T>> {
    private NodoAVL<T> raiz;
    int cont;

    // fe = (altura del subárbol derecho - altura del subárbol izquierdo)
    public ArbolAVL() {
        raiz = null;
        cont = 0;
    }

    public void insertaAVL(T elem) {
        NodoAVL<T> actual = raiz;
        NodoAVL<T> nuevo = new NodoAVL<>(elem);
        NodoAVL<T> papa = null;
        boolean encontre = false;
        while (actual != null && !encontre) {
            papa = actual;
            if (elem.compareTo(actual.elem) < 0) {
                actual = actual.izq;
            } else if (elem.compareTo(actual.elem) > 0) {
                actual = actual.der;
            } else {
                encontre = true; // El elemento ya existe
            }
        }
        if (encontre) {
            return; 
        }
        if (papa == null) { 
            raiz = nuevo;
        } else if (elem.compareTo(papa.elem) < 0) {
            papa.izq = nuevo;
        } else {
            papa.der = nuevo;
        }
        nuevo.papa = papa;
        cont++;
        if (nuevo.papa != null && !nuevo.papa.equals(raiz)) {
            actualizaFeHastaRaiz(nuevo.papa);
        }
    }
    
    // se balancea el árbol
    private NodoAVL<T> roto(NodoAVL<T> actual) {
        NodoAVL<T> alfa, beta, gamma, A, B, C, D, papa;
        papa = actual.papa;
        
        // Caso izq - izq (desbalanceado a la izquierda, subárbol izquierdo más alto)
        if (actual.fe == -2 && actual.izq.fe <= 0) {
            papa = actual.papa;
            alfa = actual;
            beta = alfa.izq;
            gamma = beta.izq;
            A = gamma.izq;
            B = gamma.der;
            C = beta.der;
            D = alfa.der;
            alfa.izq = C;
            if (C != null) {
                C.papa = alfa;
            }
            beta.izq = gamma;
            gamma.papa = beta;
            beta.der = alfa;
            alfa.papa = beta;
            if (papa == null) {
                raiz = beta;
                beta.papa = null;
            } else {
                if (beta.elem.compareTo(papa.elem) < 0) {
                    papa.izq = beta;
                } else {
                    papa.der = beta;
                }
                beta.papa = papa;
            }
            actualizaFe(alfa);
            actualizaFe(beta);
            if (gamma != null) {
                actualizaFe(gamma);
            }
            return beta;
        }
        
        // Caso der - der (desbalanceado a la derecha, subárbol derecho más alto)
        if (actual.fe == 2 && actual.der.fe >= 0) {
            papa = actual.papa;
            alfa = actual;
            beta = alfa.der;
            gamma = beta.der;
            A = alfa.izq;
            B = beta.izq;
            C = gamma.izq;
            D = gamma.der;
            alfa.der = B;
            if (B != null) {
                B.papa = alfa;
            }
            beta.der = gamma;
            gamma.papa = beta;
            beta.izq = alfa;
            alfa.papa = beta;
            if (papa == null) {
                raiz = beta;
                beta.papa = null;
            } else {
                if (beta.elem.compareTo(papa.elem) < 0) {
                    papa.izq = beta;
                } else {
                    papa.der = beta;
                }
                beta.papa = papa;
            }
            actualizaFe(alfa);
            actualizaFe(beta);
            if (gamma != null) {
                actualizaFe(gamma);
            }
            return beta;
        }
        
        // Caso der - izq (desbalanceado a la derecha, subárbol izquierdo más alto)
        if (actual.fe == 2 && actual.der.fe == -1) {
            alfa = actual;
            A = alfa.izq;
            beta = alfa.der;
            gamma = beta.izq;
            D = beta.der;
            B = gamma.izq;
            C = gamma.der;
            if (A != null) {
                alfa.izq = A;
                A.papa = alfa;
            }
            alfa.der = B;
            if (B != null) {
                B.papa = alfa;
            }
            beta.izq = C;
            if (C != null) {
                C.papa = beta;
            }
            beta.der = D;
            if (D != null) {
                D.papa = beta;
            }
            gamma.izq = alfa;
            alfa.papa = gamma;
            gamma.der = beta;
            beta.papa = gamma;
            if (papa == null) {
                raiz = gamma;
                gamma.papa = null;
            } else {
                if (gamma.elem.compareTo(papa.elem) < 0) {
                    papa.izq = gamma;
                } else {
                    papa.der = gamma;
                }
                gamma.papa = papa;
            }
            actualizaFe(alfa);
            actualizaFe(beta);
            actualizaFe(gamma);
            return gamma;
        }
        
        // Caso izq - der (desbalanceado a la izquierda, subárbol derecho más alto)
        if (actual.fe == -2 && actual.izq.fe == 1) {
            alfa = actual;
            beta = alfa.izq;
            gamma = beta.der;
            A = beta.izq;
            B = gamma.izq;
            C = gamma.der;
            D = alfa.der;
            beta.der = B;
            if (B != null) {
                B.papa = beta;
            }
            gamma.izq = beta;
            beta.papa = gamma;
            alfa.izq = C;
            if (C != null) {
                C.papa = alfa;
            }
            gamma.der = alfa;
            alfa.papa = gamma;
            if (papa == null) {
                raiz = gamma;
                gamma.papa = null;
            } else {
                if (gamma.elem.compareTo(papa.elem) < 0) {
                    papa.izq = gamma;
                } else {
                    papa.der = gamma;
                }
                gamma.papa = papa;
            }
            actualizaFe(alfa);
            actualizaFe(beta);
            actualizaFe(gamma);
            return gamma;
        }
        
        return actual;
    }
    
    public boolean busca(T elem) {
        return buscaAux(raiz, elem);
    }

    private boolean buscaAux(NodoAVL<T> actual, T elem) {
        if (actual == null) {
            return false;
        } else if (elem.compareTo(actual.elem) < 0) {
            return buscaAux(actual.izq, elem);
        } else if (elem.compareTo(actual.elem) > 0) {
            return buscaAux(actual.der, elem);
        } else {
            return true;
        }
    }

    // Recorrido preorden 
    // proceso
    // nodo izquierdo
    // nodo derecho
    public ArrayList<NodoAVL<T>> preOrden() {
        ArrayList<NodoAVL<T>> lista = new ArrayList<>();
        preOrdenAux(raiz, lista);
        return lista;
    }

    private void preOrdenAux(NodoAVL<T> actual, ArrayList<NodoAVL<T>> lista) {
        if (actual == null) {
            return;
        }
        lista.add(actual);
        preOrdenAux(actual.izq, lista);
        preOrdenAux(actual.der, lista);
    }

    // Recorrido inOrden 
    // nodo izquierdo
    // proceso
    // nodo derecho
    public ArrayList<NodoAVL<T>> inOrden() {
        ArrayList<NodoAVL<T>> lista = new ArrayList<>();
        inOrdenAux(raiz, lista);
        return lista;
    }

    private void inOrdenAux(NodoAVL<T> actual, ArrayList<NodoAVL<T>> lista) {
        if (actual == null) {
            return;
        }
        inOrdenAux(actual.izq, lista);
        lista.add(actual);
        inOrdenAux(actual.der, lista);
    }

    // Recorrido postOrden 
    // nodo izquierdo
    // nodo derecho
    // proceso
    public ArrayList<NodoAVL<T>> postOrden() {
        ArrayList<NodoAVL<T>> lista = new ArrayList<>();
        postOrdenAux(raiz, lista);
        return lista;
    }

    public void postOrdenAux(NodoAVL<T> nodo, ArrayList<NodoAVL<T>> lista) {
        if (nodo == null) {
            return;
        }
        postOrdenAux(nodo.izq, lista);
        postOrdenAux(nodo.der, lista);
        lista.add(nodo);
    }

    // Cálculo de la altura del árbol
    public int altura() {
        return alturaAux(raiz);
    }

    private int alturaAux(NodoAVL<T> actual) {
        if (actual == null) {
            return -1;
        }
        int altIzq = alturaAux(actual.izq);
        int altDer = alturaAux(actual.der);
        return Math.max(altIzq, altDer) + 1;
    }

    // Borra un elemento y actualiza el balance.
    public void borrarAVL(T dato) {
        if (busca(dato)) {
            raiz = borrarAux(raiz, dato);
            // Si el árbol no queda vacío, actualizamos los factores de equilibrio y rebalanceamos desde la raíz.
            if (raiz != null) {
                actualizaFeHastaRaiz(raiz);
            }
        } else {
            System.out.println(dato + " no se encontró");
        }
    }    

    private NodoAVL<T> borrarAux(NodoAVL<T> actual, T dato) {
        if (actual == null) {
            return actual;
        }
        int cmp = dato.compareTo(actual.elem);
        if (cmp < 0) {
            actual.izq = borrarAux(actual.izq, dato);
            if (actual.izq != null) {
                actual.izq.papa = actual;
            }
        } else if (cmp > 0) {
            actual.der = borrarAux(actual.der, dato);
            if (actual.der != null) {
                actual.der.papa = actual;
            }
        } else {
            // Nodo a borrar encontrado
            if (actual.izq == null && actual.der == null) {
                return null;
            } else if (actual.der != null) {
                actual.elem = sucesor(actual);
                actual.der = borrarAux(actual.der, actual.elem);
                if (actual.der != null) {
                    actual.der.papa = actual;
                }
            } else {
                actual.elem = predecesor(actual);
                actual.izq = borrarAux(actual.izq, actual.elem);
                if (actual.izq != null) {
                    actual.izq.papa = actual;
                }
            }
        }
        return actual;
    }
    

    private T sucesor(NodoAVL<T> actual) {
        NodoAVL<T> temp = actual.der;
        while (temp.izq != null) {
            temp = temp.izq;
        }
        return temp.elem;
    }

    private T predecesor(NodoAVL<T> actual) {
        NodoAVL<T> temp = actual.izq;
        while (temp.der != null) {
            temp = temp.der;
        }
        return temp.elem;
    }

    @Override
    public String toString() {
        String aux = "";
        Iterator<NodoAVL<T>> auxIt= inOrden().iterator();
        while (auxIt.hasNext()) {
            aux += auxIt.next().elem + "\n";
        }
        return aux;
    }

    // actualiza Fe del nodo hacia abajo
    public void actualizaFe(NodoAVL<T> nodo) {
        if (nodo != null) {
            nodo.fe = alturaAux(nodo.der) - alturaAux(nodo.izq);
        }
    }

    // actualiza Fe del nodo hacia la raíz, rotando si es necesario
    private void actualizaFeHastaRaiz(NodoAVL<T> nodo) {
        while (nodo != null) {
            actualizaFe(nodo);
            if (Math.abs(nodo.fe) == 2) {
                nodo = roto(nodo);
            } else {
                nodo = nodo.papa;
            }
        }
    }


    public static void main(String[] args) {
        ArbolAVL<Integer> arbol = new ArbolAVL<>();
        arbol.insertaAVL(10);
        arbol.insertaAVL(5);
        arbol.insertaAVL(15);
        arbol.insertaAVL(3);
        arbol.insertaAVL(7);
        arbol.insertaAVL(13);
        arbol.insertaAVL(17);
        arbol.insertaAVL(1);
        arbol.insertaAVL(4);
        arbol.insertaAVL(6);
        System.out.println(arbol.toString());
        
        arbol.borrarAVL(5);
        arbol.borrarAVL(15);
        arbol.borrarAVL(90);
        arbol.borrarAVL(13);
        arbol.borrarAVL(17);
        System.out.println(arbol.toString());

        arbol.insertaAVL(10);
        arbol.insertaAVL(5);
        arbol.insertaAVL(15);
        arbol.insertaAVL(3);
        arbol.insertaAVL(7);
        System.out.println(arbol.toString());
        
    }
}
