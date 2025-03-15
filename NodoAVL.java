
public class NodoAVL<T extends Comparable<T>> {
    public T elem;
    public NodoAVL<T> izq, der, papa;
    int fe;

    public NodoAVL(T elem) {
        this.elem = elem;
        this.izq = null;
        this.der = null;
        this.papa = null;
        this.fe = 0;
    }
    
    public NodoAVL() {
        this.elem = null;
        this.izq = null;
        this.der = null;
        this.papa = null;
        this.fe = 0;
    }

    public T getElem() {
        return elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }
    
    // Métodos auxiliares para verificar la existencia de hijos
    public boolean hasIzq() {
        return izq != null;
    }
    
    public boolean hasDer() {
        return der != null;
    }
}

