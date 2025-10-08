package arbolRojinegroProject;

import arbolRojinegroProject.utils.Nodo;

/**
 * Implementación de un Árbol Rojo-Negro (Red-Black Tree)
 * Propiedades:
 * 1. Cada nodo es rojo o negro
 * 2. La raíz es negra
 * 3. Todas las hojas (NIL) son negras
 * 4. Si un nodo es rojo, sus hijos son negros
 * 5. Todos los caminos de un nodo a sus hojas descendientes contienen el mismo número de nodos negros
 */
public class Arbol {
    
    private Nodo root;
    private final Nodo TNULL; // Nodo centinela (representa hojas nulas)

    /**
     * Constructor del árbol rojo-negro
     * Inicializa el árbol con un nodo centinela TNULL
     */
    public Arbol() {
        TNULL = new Nodo();
        TNULL.setColor(Nodo.COLOR_NEGRO);
        TNULL.setLeft(null);
        TNULL.setRight(null);
        root = TNULL;
    }

    /**
     * Inserta un nuevo elemento en el árbol manteniendo las propiedades del árbol rojo-negro
     * @param key El valor a insertar
     */
    public void insertar(int key) {
        Nodo nuevoNodo = crearNodo(key);
        Nodo padre = null;
        Nodo actual = this.root;

        // Búsqueda BST estándar para encontrar la posición de inserción
        while (actual != TNULL) {
            padre = actual;
            if (nuevoNodo.getData() < actual.getData()) {
                actual = actual.getLeft();
            } else {
                actual = actual.getRight();
            }
        }

        // Asignar el padre al nuevo nodo
        nuevoNodo.setFather(padre);
        
        // Insertar el nodo en la posición correcta
        if (padre == null) {
            // El árbol estaba vacío
            root = nuevoNodo;
        } else if (nuevoNodo.getData() < padre.getData()) {
            padre.setLeft(nuevoNodo);
        } else {
            padre.setRight(nuevoNodo);
        }

        // Si el nuevo nodo es la raíz, colorearlo de negro y terminar
        if (nuevoNodo.getFather() == null) {
            nuevoNodo.setColor(Nodo.COLOR_NEGRO);
            return;
        }

        // Si el padre del nuevo nodo es la raíz, no hay violaciones
        if (nuevoNodo.getFather().getFather() == null) {
            return;
        }

        // Corregir las violaciones de las propiedades del árbol rojo-negro
        arreglarInsert(nuevoNodo);
    }

    /**
     * Crea un nuevo nodo con el valor especificado
     * @param key El valor del nodo
     * @return El nodo creado
     */
    private Nodo crearNodo(int key) {
        Nodo nodo = new Nodo();
        nodo.setFather(null);
        nodo.setData(key);
        nodo.setLeft(TNULL);
        nodo.setRight(TNULL);
        nodo.setColor(Nodo.COLOR_ROJO); // Los nuevos nodos siempre son rojos
        return nodo;
    }

    /**
     * Corrige el árbol después de la inserción para mantener las propiedades del árbol rojo-negro
     * @param nodo El nodo recién insertado
     */
    private void arreglarInsert(Nodo nodo) {
        Nodo tio;
        
        // Mientras el padre sea rojo (violación de la propiedad 4)
        while (nodo.getFather() != null && nodo.getFather().esRojo()) {
            Nodo abuelo = nodo.getFather().getFather();
            
            if (abuelo == null) {
                break;
            }
            
            if (nodo.getFather() == abuelo.getRight()) {
                // El padre es hijo derecho del abuelo
                tio = abuelo.getLeft();
                
                if (tio.esRojo()) {
                    // Caso 1: El tío es rojo - Recolorear
                    tio.setColor(Nodo.COLOR_NEGRO);
                    nodo.getFather().setColor(Nodo.COLOR_NEGRO);
                    abuelo.setColor(Nodo.COLOR_ROJO);
                    nodo = abuelo;
                } else {
                    // El tío es negro
                    if (nodo == nodo.getFather().getLeft()) {
                        // Caso 2: El nodo es hijo izquierdo - Rotación derecha en el padre
                        nodo = nodo.getFather();
                        rotarDerecha(nodo);
                    }
                    // Caso 3: El nodo es hijo derecho - Rotación izquierda en el abuelo
                    nodo.getFather().setColor(Nodo.COLOR_NEGRO);
                    if (nodo.getFather().getFather() != null) {
                        nodo.getFather().getFather().setColor(Nodo.COLOR_ROJO);
                        rotarIzquierda(nodo.getFather().getFather());
                    }
                }
            } else {
                // El padre es hijo izquierdo del abuelo (simétrico)
                tio = abuelo.getRight();

                if (tio.esRojo()) {
                    // Caso 1: El tío es rojo - Recolorear
                    tio.setColor(Nodo.COLOR_NEGRO);
                    nodo.getFather().setColor(Nodo.COLOR_NEGRO);
                    abuelo.setColor(Nodo.COLOR_ROJO);
                    nodo = abuelo;
                } else {
                    // El tío es negro
                    if (nodo == nodo.getFather().getRight()) {
                        // Caso 2: El nodo es hijo derecho - Rotación izquierda en el padre
                        nodo = nodo.getFather();
                        rotarIzquierda(nodo);
                    }
                    // Caso 3: El nodo es hijo izquierdo - Rotación derecha en el abuelo
                    nodo.getFather().setColor(Nodo.COLOR_NEGRO);
                    if (nodo.getFather().getFather() != null) {
                        nodo.getFather().getFather().setColor(Nodo.COLOR_ROJO);
                        rotarDerecha(nodo.getFather().getFather());
                    }
                }
            }
            
            // Si llegamos a la raíz, salir
            if (nodo == root) {
                break;
            }
        }
        
        // La raíz siempre debe ser negra (propiedad 2)
        root.setColor(Nodo.COLOR_NEGRO);
    }

    /**
     * Realiza una rotación a la izquierda sobre el nodo x
     * @param x El nodo sobre el cual rotar
     */
    private void rotarIzquierda(Nodo x) {
        Nodo y = x.getRight();
        x.setRight(y.getLeft());
        
        if (y.getLeft() != TNULL) {
            y.getLeft().setFather(x);
        }
        
        y.setFather(x.getFather());
        
        if (x.getFather() == null) {
            this.root = y;
        } else if (x == x.getFather().getLeft()) {
            x.getFather().setLeft(y);
        } else {
            x.getFather().setRight(y);
        }
        
        y.setLeft(x);
        x.setFather(y);
    }

    /**
     * Realiza una rotación a la derecha sobre el nodo x
     * @param x El nodo sobre el cual rotar
     */
    private void rotarDerecha(Nodo x) {
        Nodo y = x.getLeft();
        x.setLeft(y.getRight());
        
        if (y.getRight() != TNULL) {
            y.getRight().setFather(x);
        }
        
        y.setFather(x.getFather());
        
        if (x.getFather() == null) {
            this.root = y;
        } else if (x == x.getFather().getRight()) {
            x.getFather().setRight(y);
        } else {
            x.getFather().setLeft(y);
        }
        
        y.setRight(x);
        x.setFather(y);
    }

    /**
     * Obtiene la raíz del árbol
     * @return El nodo raíz
     */
    public Nodo getRoot() {
        return this.root;
    }

    /**
     * Obtiene el nodo centinela TNULL
     * @return El nodo TNULL
     */
    public Nodo getTNULL() {
        return this.TNULL;
    }

    /**
     * Busca un nodo con el valor especificado
     * @param key El valor a buscar
     * @return El nodo encontrado o TNULL si no existe
     */
    public Nodo buscar(int key) {
        return buscarAux(this.root, key);
    }

    /**
     * Método auxiliar recursivo para buscar un valor
     * @param nodo El nodo actual
     * @param key El valor a buscar
     * @return El nodo encontrado o TNULL si no existe
     */
    private Nodo buscarAux(Nodo nodo, int key) {
        if (nodo == TNULL || key == nodo.getData()) {
            return nodo;
        }

        if (key < nodo.getData()) {
            return buscarAux(nodo.getLeft(), key);
        }
        return buscarAux(nodo.getRight(), key);
    }

    /**
     * Verifica si el árbol está vacío
     * @return true si el árbol está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        return root == TNULL;
    }

    /**
     * Realiza un recorrido inorden del árbol
     * @return String con los valores en orden
     */
    public String recorridoInorden() {
        StringBuilder sb = new StringBuilder();
        recorridoInordenAux(this.root, sb);
        return sb.toString().trim();
    }

    /**
     * Método auxiliar para el recorrido inorden
     * @param nodo El nodo actual
     * @param sb StringBuilder para acumular los valores
     */
    private void recorridoInordenAux(Nodo nodo, StringBuilder sb) {
        if (nodo != TNULL) {
            recorridoInordenAux(nodo.getLeft(), sb);
            sb.append(nodo.getData()).append(" ");
            recorridoInordenAux(nodo.getRight(), sb);
        }
    }

    /**
     * Imprime el árbol de forma visual
     */
    public void imprimirArbol() {
        imprimirArbolAux(this.root, "", true);
    }

    /**
     * Método auxiliar para imprimir el árbol
     * @param nodo El nodo actual
     * @param prefijo El prefijo para la indentación
     * @param esDerecho true si es hijo derecho, false si es izquierdo
     */
    private void imprimirArbolAux(Nodo nodo, String prefijo, boolean esDerecho) {
        if (nodo != TNULL) {
            System.out.println(prefijo + (esDerecho ? "└── " : "┌── ") + nodo);
            imprimirArbolAux(nodo.getRight(), prefijo + (esDerecho ? "    " : "│   "), true);
            imprimirArbolAux(nodo.getLeft(), prefijo + (esDerecho ? "    " : "│   "), false);
        }
    }

    /**
     * Obtiene la altura del árbol
     * @return La altura del árbol
     */
    public int obtenerAltura() {
        return obtenerAlturaAux(this.root);
    }

    /**
     * Método auxiliar para calcular la altura
     * @param nodo El nodo actual
     * @return La altura desde ese nodo
     */
    private int obtenerAlturaAux(Nodo nodo) {
        if (nodo == TNULL) {
            return 0;
        }
        int alturaIzq = obtenerAlturaAux(nodo.getLeft());
        int alturaDer = obtenerAlturaAux(nodo.getRight());
        return 1 + Math.max(alturaIzq, alturaDer);
    }

    /**
     * Cuenta el número total de nodos en el árbol
     * @return El número de nodos
     */
    public int contarNodos() {
        return contarNodosAux(this.root);
    }

    /**
     * Método auxiliar para contar nodos
     * @param nodo El nodo actual
     * @return El número de nodos desde ese nodo
     */
    private int contarNodosAux(Nodo nodo) {
        if (nodo == TNULL) {
            return 0;
        }
        return 1 + contarNodosAux(nodo.getLeft()) + contarNodosAux(nodo.getRight());
    }
}
