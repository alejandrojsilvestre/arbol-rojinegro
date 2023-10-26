package arbolRojinegroProject;

import arbolRojinegroProject.utils.Nodo;

public class Arbol {
    
    private Nodo root;
    private Nodo tnull; // Nodo nulo (representa hojas nulas)

    // Constructor
    public Arbol() {
        tnull = new Nodo();
        tnull.color = 0; // Color del Nodo nulo es negro
        root = tnull;
    }

    // Insertar un elemento en el árbol
    public void insertar(int key) {
        Nodo Nodo = new Nodo();
        Nodo.father = null;
        Nodo.data = key;
        Nodo.left = tnull;
        Nodo.right = tnull;
        Nodo.color = 1; // Nuevo Nodo siempre es rojo

        Nodo y = null;
        Nodo x = this.root;

        while (x != tnull) {
            y = x;
            if (Nodo.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        // y es el padre de x
        Nodo.father = y;
        if (y == null) {
            root = Nodo;
        } else if (Nodo.data < y.data) {
            y.left = Nodo;
        } else {
            y.right = Nodo;
        }

        // Si el nuevo Nodo es una raíz, no hay necesidad de arreglos adicionales
        if (Nodo.father == null){
            Nodo.color = 0;
            return;
        }

        // Si el abuelo se vuelve rojo y el padre es rojo
        if (Nodo.father != null && Nodo.father.father != null) {
            arreglarInsert(Nodo);
        }
    }

    // Corregir el árbol después de la inserción
    private void arreglarInsert(Nodo k){
        Nodo u;
        while (k.father.color == 1) {
            if (k.father == k.father.father.right) {
                // Si el padre es un hijo derecho
                u = k.father.father.left; // Tío
                if (u.color == 1) {
                    // Caso 3.1
                    u.color = 0;
                    k.father.color = 0;
                    k.father.father.color = 1;
                    k = k.father.father;
                } else {
                    if (k == k.father.left) {
                        // Caso 3.2.2
                        k = k.father;
                        rotarDerecha(k);
                    }
                    // Caso 3.2.1
                    k.father.color = 0;
                    k.father.father.color = 1;
                    rotarIzquierda(k.father.father);
                }
            } else {
                // Si el padre es un hijo izquierdo
                u = k.father.father.right; // Tío

                if (u.color == 1) {
                    // Caso 3.1
                    u.color = 0;
                    k.father.color = 0;
                    k.father.father.color = 1;
                    k = k.father.father; 
                } else {
                    if (k == k.father.right) {
                        // Caso 3.2.2
                        k = k.father;
                        rotarIzquierda(k);
                    }
                    // Caso 3.2.1
                    k.father.color = 0;
                    k.father.father.color = 1;
                    rotarDerecha(k.father.father);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    // Rotación a la izquierda
    private void rotarIzquierda(Nodo x) {
        Nodo y = x.right;
        x.right = y.left;
        if (y.left != tnull) {
            y.left.father = x;
        }
        y.father = x.father;
        if (x.father == null) {
            this.root = y;
        } else if (x == x.father.left) {
            x.father.left = y;
        } else {
            x.father.right = y;
        }
        y.left = x;
        x.father = y;
    }

    // Rotación a la derecha
    private void rotarDerecha(Nodo x) {
        Nodo y = x.left;
        x.left = y.right;
        if (y.right != tnull) {
            y.right.father = x;
        }
        y.father = x.father;
        if (x.father == null) {
            this.root = y;
        } else if (x == x.father.right) {
            x.father.right = y;
        } else {
            x.father.left = y;
        }
        y.right = x;
        x.father = y;
    }
}
