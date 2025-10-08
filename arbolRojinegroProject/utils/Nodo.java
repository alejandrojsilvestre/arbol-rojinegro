package arbolRojinegroProject.utils;

/**
 * Representa un nodo en un árbol rojo-negro
 * Cada nodo contiene un valor entero, referencias a su padre y sus hijos,
 * y un color (rojo o negro)
 */
public class Nodo {
    
    // Constantes para los colores
    public static final int COLOR_NEGRO = 0;
    public static final int COLOR_ROJO = 1;
    
    // Datos del nodo
    private int data;
    private Nodo father;
    private Nodo left;
    private Nodo right;
    private int color;

    /**
     * Constructor por defecto
     * Inicializa un nodo negro con valor 0
     */
    public Nodo() {
        this.data = 0;
        this.father = null;
        this.left = null;
        this.right = null;
        this.color = COLOR_NEGRO;
    }

    /**
     * Constructor con valor
     * @param data El valor del nodo
     */
    public Nodo(int data) {
        this.data = data;
        this.father = null;
        this.left = null;
        this.right = null;
        this.color = COLOR_ROJO; // Por defecto, los nuevos nodos son rojos
    }

    /**
     * Constructor completo
     * @param data El valor del nodo
     * @param color El color del nodo (COLOR_ROJO o COLOR_NEGRO)
     */
    public Nodo(int data, int color) {
        this.data = data;
        this.father = null;
        this.left = null;
        this.right = null;
        this.color = color;
    }

    // Getters y Setters
    
    /**
     * Obtiene el valor del nodo
     * @return El valor almacenado
     */
    public int getData() {
        return data;
    }

    /**
     * Establece el valor del nodo
     * @param data El nuevo valor
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * Obtiene el padre del nodo
     * @return El nodo padre
     */
    public Nodo getFather() {
        return father;
    }

    /**
     * Establece el padre del nodo
     * @param father El nuevo nodo padre
     */
    public void setFather(Nodo father) {
        this.father = father;
    }

    /**
     * Obtiene el hijo izquierdo
     * @return El nodo hijo izquierdo
     */
    public Nodo getLeft() {
        return left;
    }

    /**
     * Establece el hijo izquierdo
     * @param left El nuevo hijo izquierdo
     */
    public void setLeft(Nodo left) {
        this.left = left;
    }

    /**
     * Obtiene el hijo derecho
     * @return El nodo hijo derecho
     */
    public Nodo getRight() {
        return right;
    }

    /**
     * Establece el hijo derecho
     * @param right El nuevo hijo derecho
     */
    public void setRight(Nodo right) {
        this.right = right;
    }

    /**
     * Obtiene el color del nodo
     * @return El color (COLOR_ROJO o COLOR_NEGRO)
     */
    public int getColor() {
        return color;
    }

    /**
     * Establece el color del nodo
     * @param color El nuevo color (COLOR_ROJO o COLOR_NEGRO)
     */
    public void setColor(int color) {
        if (color != COLOR_ROJO && color != COLOR_NEGRO) {
            throw new IllegalArgumentException("Color inválido. Use COLOR_ROJO o COLOR_NEGRO");
        }
        this.color = color;
    }

    /**
     * Verifica si el nodo es rojo
     * @return true si el nodo es rojo, false en caso contrario
     */
    public boolean esRojo() {
        return this.color == COLOR_ROJO;
    }

    /**
     * Verifica si el nodo es negro
     * @return true si el nodo es negro, false en caso contrario
     */
    public boolean esNegro() {
        return this.color == COLOR_NEGRO;
    }

    /**
     * Verifica si el nodo es una hoja (nodo centinela)
     * @return true si es una hoja, false en caso contrario
     */
    public boolean esHoja() {
        return this.left == null && this.right == null;
    }

    /**
     * Verifica si el nodo tiene dos hijos
     * @return true si tiene dos hijos, false en caso contrario
     */
    public boolean tieneDosHijos() {
        return this.left != null && this.right != null;
    }

    /**
     * Obtiene el abuelo del nodo
     * @return El nodo abuelo o null si no existe
     */
    public Nodo getAbuelo() {
        if (this.father != null) {
            return this.father.father;
        }
        return null;
    }

    /**
     * Obtiene el tío del nodo (hermano del padre)
     * @return El nodo tío o null si no existe
     */
    public Nodo getTio() {
        Nodo abuelo = getAbuelo();
        if (abuelo == null) {
            return null;
        }
        if (this.father == abuelo.left) {
            return abuelo.right;
        } else {
            return abuelo.left;
        }
    }

    /**
     * Obtiene el hermano del nodo
     * @return El nodo hermano o null si no existe
     */
    public Nodo getHermano() {
        if (this.father == null) {
            return null;
        }
        if (this == this.father.left) {
            return this.father.right;
        } else {
            return this.father.left;
        }
    }

    /**
     * Representa el nodo como una cadena de texto
     * @return Representación en String del nodo
     */
    @Override
    public String toString() {
        String colorStr = (color == COLOR_ROJO) ? "ROJO" : "NEGRO";
        return "Nodo{" +
                "data=" + data +
                ", color=" + colorStr +
                '}';
    }

    /**
     * Compara este nodo con otro objeto
     * @param obj El objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Nodo nodo = (Nodo) obj;
        return data == nodo.data && color == nodo.color;
    }

    /**
     * Genera un código hash para el nodo
     * @return El código hash
     */
    @Override
    public int hashCode() {
        int result = data;
        result = 31 * result + color;
        return result;
    }
}