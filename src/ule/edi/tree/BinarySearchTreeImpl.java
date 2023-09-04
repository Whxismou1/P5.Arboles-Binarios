package ule.edi.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * arbol binario de busqueda (binary search tree, BST).
 * 
 * El codigo fuente esta en UTF-8, y la constante EMPTY_TREE_MARK definida en
 * AbstractTreeADT del proyecto API deberia ser el simbolo de conjunto vacio: ∅
 * 
 * Si aparecen caracteres "raros", es porque el proyecto no esta bien
 * configurado en Eclipse para usar esa codificacion de caracteres.
 *
 * En el toString() que esta ya implementado en AbstractTreeADT se usa el
 * formato:
 * 
 * Un arbol vaci­o se representa como "∅". Un Ã¡rbol no vacio como
 * "{(informacion rai­z), sub-arbol 1, sub-arbol 2, ...}".
 * 
 * Por ejemplo, {A, {B, ∅, ∅}, ∅} es un arbol binario con rai­z "A" y un unico
 * sub-arbol, a su izquierda, con rai­z "B".
 * 
 * El metodo render() tambien representa un arbol, pero con otro formato; por
 * ejemplo, un arbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se muestra como:
 * 
 * M | E | | ∅ | | ∅ | S | | ∅ | | ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para adjuntar
 * informacion extra. Si es el caso, tanto toString() como render() mostraran
 * los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor) y con
 * {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en los
 * elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> seria muy restrictivo; en su lugar se
 * permiten tipos que sean comparables no solo con exactamente T sino tambien
 * con tipos por encima de T en la herencia.
 * 
 * @param <T> tipo de la informacion en cada nodo, comparable.
 */
public class BinarySearchTreeImpl<T extends Comparable<? super T>> extends AbstractBinaryTreeADT<T> {

	BinarySearchTreeImpl<T> father; // referencia a su nodo padre)
	int count; // contador de instancias

	/**
	 * Devuelve el arbol binario de busqueda izquierdo.
	 */
	protected BinarySearchTreeImpl<T> getLeftBST() {
		// El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		// aqui­ se sabe que es ademas BST (binario de busqueda)
		//
		return (BinarySearchTreeImpl<T>) left;
	}

	protected void setLeftBST(BinarySearchTreeImpl<T> left) {
		this.left = left;
	}

	/**
	 * Devuelve el arbol binario de busqueda derecho.
	 */
	protected BinarySearchTreeImpl<T> getRightBST() {
		return (BinarySearchTreeImpl<T>) right;
	}

	protected void setRightBST(BinarySearchTreeImpl<T> right) {
		this.right = right;
	}

	/**
	 * arbol BST vaci­o
	 */
	public BinarySearchTreeImpl() {
		// TODO HACER QUE THIS SEA EL NODO VACIO
		this.content = null;
		this.count = 0;
		this.left = null;
		this.right = null;
	}

	public BinarySearchTreeImpl(BinarySearchTreeImpl<T> father) {
		// TODO HACER QUE THIS SEA EL NODO VACIO, asignando como padre el parametro
		// recibido
		this.content = null;
		this.count = 0;
		this.father = father;
		this.left = null;
		this.right = null;
	}

	private BinarySearchTreeImpl<T> emptyBST(BinarySearchTreeImpl<T> father) {
		// Devuelve un nodo vacío
		return new BinarySearchTreeImpl<T>(father);
	}

	/**
	 * Inserta los elementos que no sean null, de una coleccion en el arbol. (si
	 * alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements valores a insertar.
	 * @return numero de elementos insertados en el arbol (elementos diferentes de
	 *         null)
	 */
	public int insert(Collection<T> elements) {
		// si alguno es 'null', no se inserta
		// TODO Implementar el metodo
		int count = 0;
		for (T elem : elements) {
			if (elem != null) {
				count++;
				insert(elem);
			}
		}

		return count;
	}

	/**
	 * Inserta los elementos que no sean null, de un array en el arbol. (si alguno
	 * es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 * @return numero de elementos insertados en el arbol (elementos diferentes de
	 *         null)
	 */
	public int insert(T... elements) {

		// si alguno es 'null', no inserta ese elemento
		// TODO Implementar el metodo
		int count = 0;

		for (T elem : elements) {
			if (elem != null) {
				count++;
				insert(elem);
			}
		}

		return count;
	}

	/**
	 * Inserta (como hoja) un nuevo elemento en el arbol de busqueda.
	 * 
	 * Debe asignarse valor a su atributo father (referencia a su nodo padre o null
	 * si es la rai­z)
	 * 
	 * No se permiten elementos null. Si element es null dispara
	 * excepcion:IllegalArgumentException Si el elemento ya existe en el arbol no
	 * inserta un nodo nuevo, sino que incrementa el atributo count del nodo que
	 * tiene igual contenido.
	 * 
	 * @param element valor a insertar.
	 * @return true si se insertó en un nuevo nodo (no existia ese elemento en el
	 *         arbol), false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 */
	public boolean insert(T element) {
		// TODO Implementar el metodo
		this.checkIsNull(element);

		if (this.isEmpty()) {
			this.setLeftBST(emptyBST(this));
			this.setRightBST(emptyBST(this));
			this.count++;
			this.content = element;
			return true;
		} else if (this.content.equals(element)) {
			this.count++;
			return false;
		} else if (element.compareTo(this.content) < 0) {
			return getLeftBST().insert(element);
		} else {
			return getRightBST().insert(element);

		}
	}

	/**
	 * Busca el elemento en el arbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param element valor a buscar.
	 * @return true si el elemento esta en el arbol, false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 *
	 */
	public boolean contains(T element) {
		// TODO Implementar el metodo
		this.checkIsNull(element);

		if (!isEmpty()) {
			if (this.content.equals(element)) {
				return true;
			} else if (this.content.compareTo(element) < 0) {
				return getRightBST().contains(element);
			} else if (this.content.compareTo(element) > 0) {
				return getLeftBST().contains(element);
			}

		}

		return false;
	}

	/**
	 * devuelve la cadena formada por el contenido del árbol teniendo en cuenta que
	 * si un nodo tiene su atributo count>1 pone entre paréntesis su valor justo
	 * detrás del atributo elem También debe mostrar las etiquetas que tenga el nodo
	 * (si las tiene)
	 * 
	 * CONSEJO: REVISAR LA IMPLEMENTACIÓN DE TOSTRING DE LA CLASE AbstractTreeADT
	 * 
	 * Por ejemplo: {M, {E(2), ∅, ∅}, {K(5), ∅, ∅}}
	 * 
	 * @return cadena con el contenido del árbol incluyendo su atributo count entre
	 *         paréntesis si elemento tiene más de 1 instancia
	 */
	public String toString() {
		// TODO implementar este metodo
		if (!isEmpty()) {
			// Construye el resultado de forma eficiente
			StringBuffer result = new StringBuffer();

			// Raíz
			result.append("{" + content.toString());

			if (count > 1) {
				result.append("(" + count + ")");
			}

			if (!tags.isEmpty()) {
				result.append(" [");

				List<String> sk = new LinkedList<String>(tags.keySet());

				Collections.sort(sk);
				for (String k : sk) {
					result.append("(" + k + ", " + tags.get(k) + "), ");
				}
				result.delete(result.length() - 2, result.length());
				result.append("]");
			}

			// Y cada sub-árbol
			for (int i = 0; i < getMaxDegree(); i++) {
				result.append(", " + getSubtree(i).toString());
			}
			// Cierra la "}" de este árbol
			result.append("}");

			return result.toString();
		} else {
			return AbstractTreeADT.EMPTY_TREE_MARK;
		}
	}

	/**
	 * Devuelve un iterador que recorre los elementos (sin tener en cuenta el número
	 * de instancias)del arbol por niveles segun el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 80, 10,
	 * 40, 60
	 * 
	 * 
	 * 
	 * @return iterador para el recorrido en anchura
	 */
	public Iterator<T> iteratorWidth() {
		// TODO Implementar metodo
		// puede implementarse creando una lista con el recorrido en anchura de los
		// elementos del arbol y devolver el iterador de dicha lista

		// Crear una cola de nodos (TreeNode)
		Queue<BinarySearchTreeImpl<T>> cola = new LinkedList<BinarySearchTreeImpl<T>>();

		// Crear una lista no-ordenada llamada resultados
		LinkedList<T> resultados = new LinkedList<>();

		// Si el árbol no es vacío:
		if (!isEmpty()) {
			// Encolar la raíz en la cola
			// cola.add((Integer) this.content);
			cola.add(this);

			// Mientras la cola no sea vacía{
			while (!cola.isEmpty()) {
				// Desencolar un elemento de la cola
				BinarySearchTreeImpl<T> node = cola.remove();
				if (node != null) {

					// Añadir el elemento al final de la lista resultados
					resultados.addLast(node.content);
					// Encolar en la cola los hijos del elemento

					if (node.getLeftBST() != null && !node.getLeftBST().isEmpty()) {
						cola.add(node.getLeftBST());
					}
					if (node.getRightBST() != null && !node.getRightBST().isEmpty()) {
						cola.add(node.getRightBST());
					}
				}
			}
		}
		return resultados.iterator();
	}

	/**
	 * Devuelve un iterador que recorre los elementos (teniendo en cuenta el número
	 * de instancias)del arbol por niveles segun el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 30, 80,
	 * 80, 10, 40, 60
	 * 
	 * @return iterador para el recorrido en anchura
	 */
	public Iterator<T> iteratorWidthInstances() {
		// TODO Implementar metodo
		// puede implementarse creando una lista con el recorrido en anchura de los
		// elementos del arbol (teniendo el número de instancias que tiene el elemento)
		// y devolver el iterador de dicha lista
		// Crear una cola de nodos (TreeNode)
		Queue<BinarySearchTreeImpl<T>> cola = new LinkedList<BinarySearchTreeImpl<T>>();

		// Crear una lista no-ordenada llamada resultados
		LinkedList<T> resultados = new LinkedList<>();

		// Si el árbol no es vacío:
		if (!isEmpty()) {
			// Encolar la raíz en la cola
			// cola.add((Integer) this.content);
			cola.add(this);

			// Mientras la cola no sea vacía{
			while (!cola.isEmpty()) {
				// Desencolar un elemento de la cola
				BinarySearchTreeImpl<T> node = cola.remove();
				if (node != null) {

					// Añadir el elemento al final de la lista resultados
					for (int i = 0; i < node.count; i++) {
						resultados.addLast(node.content);
					}
					// Encolar en la cola los hijos del elemento

					if (node.getLeftBST() != null && !node.getLeftBST().isEmpty()) {
						cola.add(node.getLeftBST());

					}
					if (node.getRightBST() != null && !node.getRightBST().isEmpty()) {
						cola.add(node.getRightBST());

					}
				}
			}
		}
		return resultados.iterator();
	}

	/**
	 * Cuenta el número de elementos diferentes del arbol (no tiene en cuenta las
	 * instancias)
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 6
	 * 
	 * @return el numero de elementos diferentes del arbol
	 */
	public int size() {
		// TODO implementar este metodo
		if (this.isEmpty()) {
			return 0;
		} else {
			return 1 + getLeftBST().size() + getRightBST().size();
		}
	}

	/**
	 * Cuenta el número de instancias de elementos diferentes del arbol
	 * 
	 * Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 11
	 * 
	 * @return el número de instancias de elementos del arbol
	 */
	public int instancesCount() {
		// TODO implementar este metodo
		if (this.isEmpty()) {
			return 0;
		} else {
			return this.count + getLeftBST().instancesCount() + getRightBST().instancesCount();
		}
	}

	/**
	 * Elimina los valores en un array del Arbol. Devuelve el número de elementos
	 * que pudo eliminar del árbol (no podrá eliminar los elemenots 'null' o que no
	 * los contiene el arbol)
	 * 
	 * return numero de elementos eliminados del arbol
	 */
	public int remove(T... elements) {
		// TODO Implementar el metodo
		int count = 0;
		for (T elem : elements) {
			if (elem != null) {
				if (contains(elem)) {
					count++;
					remove(elem);
				}
			}
		}
		return count;
	}

	/**
	 * Elimina un elemento del arbol. Si el atributo count del nodo que contiene el
	 * elemento es >1, simplemente se decrementará este valor en una unidad
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de
	 * sustituir el elemento por el menor de sus mayores y eliminar el menor de los
	 * mayores.
	 * 
	 * @throws NoSuchElementException   si el elemento a eliminar no esta en el
	 *                                  arbol
	 * @throws IllegalArgumentException si element es null
	 *
	 */
	public void remove(T element) {
		// Se comprueba que el elemento no sea null
		this.checkIsNull(element);
		if (!contains(element)) {
			throw new NoSuchElementException("ERROR: El elemento no está en la lista");
		}

		if (!isEmpty()) {
			// Si element c menor que la raiz nos vamos por la izquierda
			if (element.compareTo(this.content) < 0) {
				getLeftBST().remove(element);
				// Si element es mayor que la raiz nos vamos por la derecha
			} else if (element.compareTo(this.content) > 0) {
				getRightBST().remove(element);

				// EL caso de que sea igual
			} else if (element.equals(this.content)) {
				if (this.count > 1) {
					this.count--;
				} else {
					if (this.isLeaf()) {
						this.content = null;
						this.left = null;
						this.right = null;
						this.count = 0;
					} else if (this.getLeftBST().isEmpty() || this.getRightBST().isEmpty()) {
						BinarySearchTreeImpl<T> aux = new BinarySearchTreeImpl<>();
						if (this.getLeftBST().isEmpty()) {
							aux = getRightBST();
							this.content = aux.content;
							this.count = aux.count;
							this.left = aux.left;
							this.right = aux.right;
						} else {
							aux = getLeftBST();
							this.content = aux.content;
							this.count = aux.count;
							this.left = aux.left;
							this.right = aux.right;
						}
					} else {
						BinarySearchTreeImpl<T> aux = getRightBST();
						while (!aux.getLeftBST().isEmpty()) {
							aux = aux.getLeftBST();
						}
						T mM = aux.content;
						this.content = mM;
						this.count = aux.count;
						aux = this.getRightBST();
						aux.removeAll(mM);

					}
				}
			}

		}
	}

	/**
	 * Decrementa el número de instancias del elemento en num unidades. Si count
	 * queda en cero o negativo, se elimina el elemento del arbol. Devuelve el
	 * número de instancias que pudo eliminar
	 * 
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de
	 * sustituir el elemento por el menor de sus mayores y eliminar el menor de los
	 * mayores.
	 * 
	 * @throws NoSuchElementException   si el elemento a eliminar no esta en el
	 *                                  arbol
	 * @throws IllegalArgumentException si element es null
	 * @return numero de instancias eliminadas
	 * 
	 */
	public int remove(T element, int num) {
		// TODO Implementar el metodo
		this.checkIsNull(element);
		int count = 0;

		for (int i = 0; i < num; i++) {

			if (contains(element)) {
				remove(element);
				count++;
			}
		}
		return count;
	}

	/**
	 * Elimina todas las instancias del elemento en el árbol eliminando del arbol el
	 * nodo que contiene el elemento .
	 * 
	 * 
	 * Se tomara el criterio de sustituir el elemento por el menor de sus mayores y
	 * eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException   si el elemento a eliminar no esta en el
	 *                                  arbol
	 * @throws IllegalArgumentException si element es null
	 */
	public int removeAll(T element) {
		// TODO Implementar el metodo
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		this.checkIsNull(element);
		int count = 0;

		if (!contains(element)) {
			throw new NoSuchElementException();
		} else {

			while (contains(element)) {
				remove(element);
				count++;
			}

		}

		return count;

	}

	/**
	 * Devuelve el sub-árbol indicado. (para tests) path será el camino para obtener
	 * el sub-arbol. Está formado por 0 y 1. Si se codifica "bajar por la izquierda"
	 * como "0" y "bajar por la derecha" como "1", el camino desde un nodo N hasta
	 * un nodo M (en uno de sus sub-árboles) será * la cadena de 0s y 1s que indica
	 * cómo llegar desde N hasta M.
	 *
	 * Se define también el camino vacío desde un nodo N hasta él mismo, como cadena
	 * vacía.
	 * 
	 * Si el subarbol no existe lanzará la excepción NoSuchElementException.
	 * 
	 * @param path
	 * @return el nodo no vacío que se alcanza con ese camino
	 * @throws NoSuchElementException   si el camino no alcanza un nodo no vacío en
	 *                                  el árbol
	 * @throws IllegalArgumentException si el camino no contiene sólamente 0s y 1s
	 */
	public BinarySearchTreeImpl<T> getSubtreeWithPath(String path) {
		BinarySearchTreeImpl<T> aux = this;

		if (path.isEmpty()) {

			if (aux.isEmpty()) {
				throw new NoSuchElementException();
			}
			return aux;
		} else {

			for (int i = 0; i < path.length(); i++) {
				if (path.charAt(i) != '0' && path.charAt(i) != '1') {
					throw new IllegalArgumentException();
				}
			}

			if (!isEmpty()) {
				for (int i = 0; i < path.length(); i++) {
					if (path.charAt(i) == '0' || path.charAt(i) == '1') {
						if (path.charAt(i) == '0') {
							if (!aux.getLeftBST().isEmpty()) {
								aux = aux.getLeftBST();
							} else {
								throw new NoSuchElementException();
							}
						} else {
							if (!aux.getRightBST().isEmpty()) {
								aux = aux.getRightBST();
							} else {
								throw new NoSuchElementException();
							}
						}
					}
				}
			}

		}

		return aux;
	}

	/**
	 * Devuelve el contenido del nodo alcanzado desde la raíz de éste árbol, con el
	 * camino dado.
	 * 
	 * Por ejemplo, sea un árbol "A" {10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}:
	 * 
	 * 10 | 5 | | ∅ | | ∅ | 20 | | ∅ | | 30 | | | ∅ | | | ∅
	 * 
	 * Entonces se tiene que A.getContentWithPath("1") es 20 y que
	 * A.getContentWithPath("") es 10.
	 * 
	 * @param path camino a seguir desde la raíz.
	 * @return contenido del nodo alcanzado.
	 * @throws NoSuchElementException   si el camino no alcanza un nodo no vacío en
	 *                                  el árbol
	 * @throws IllegalArgumentException si el camino no contiene sólamente 0s y 1s
	 */
	public T getContentWithPath(String path) {
		// TODO
		return getSubtreeWithPath(path).content;
	}

	/**
	 * Etiqueta los nodos con su posición en el recorrido descendente. Por ejemplo,
	 * sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
	 * 10 | 5 | | 2 | | | ∅ | | | ∅ | | ∅ | 20 | | ∅ | | 30 | | | ∅ | | | ∅
	 * 
	 * y el árbol quedaría etiquetado como:
	 * 
	 * {10 [(descend, 3)], {5 [(descend, 4)], {2 [(descend, 5)], ∅, ∅}, ∅}, {20
	 * [(descend, 2)], ∅, {30 [(descend, 1)], ∅, ∅}}}
	 * 
	 */
	public void tagDescendent() {
		// TODO
		int[] pos = new int[1];
		pos[0] = 0;

		tagDescendentWithrec(pos);
	}

	private void tagDescendentWithrec(int[] pos) {
		if (!isEmpty()) {

			if (!getRightBST().isEmpty()) {
				getRightBST().tagDescendentWithrec(pos);
			}

			if (this != null) {
				pos[0]++;
				this.setTag("descend", pos[0]);
			}

			if (!getLeftBST().isEmpty()) {
				getLeftBST().tagDescendentWithrec(pos);
			}
		}
	}

	/**
	 * Acumula en orden postorden, una lista con los pares 'padre-hijo' en este
	 * árbol.
	 * 
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
	 * 10 | 5 | | 2 | | | ∅ | | | ∅ | | ∅ | 20 | | ∅ | | 30 | | | ∅ | | | ∅
	 * 
	 * el resultado sería una lista de cadenas:
	 * 
	 * [(5,2), (20,30), (10,5), (10,20), ]
	 * 
	 * y además quedaría etiquetado como:
	 * 
	 * {10 [(postorder, 5)], {5 [(postorder, 2)], {2 [(postorder, 1)], ∅, ∅}, ∅},
	 * {20 [(postorder, 4)], ∅, {30 [(postorder, 3)], ∅, ∅}}}
	 * 
	 * @param buffer lista con el resultado.
	 */
	public void parentChildPairsTagPostorder(List<String> buffer) {
		int[] pos = new int[1];
		pos[0] = 0;
		parentChildPairsTagPostorderWithRec(buffer, pos);
	}

	private void parentChildPairsTagPostorderWithRec(List<String> buffer, int[] pos) {
		if (!isEmpty()) {
			if (!getLeftBST().isEmpty()) {
				getLeftBST().parentChildPairsTagPostorderWithRec(buffer, pos);
			}

			if (!getRightBST().isEmpty()) {
				getRightBST().parentChildPairsTagPostorderWithRec(buffer, pos);
			}

			pos[0]++;
			this.setTag("postorder", pos[0]);

			if (!this.getLeftBST().isEmpty()) {
				String cadena = "";

				cadena += "(";
				cadena += this.content.toString();
				cadena += ",";
				cadena += this.getLeftBST().content.toString();
				cadena += ")";
				buffer.add(cadena);
			}

			if (!this.getRightBST().isEmpty()) {
				String cadena = "";

				cadena += "(";
				cadena += this.content.toString();
				cadena += ",";
				cadena += this.getRightBST().content.toString();
				cadena += ")";
				buffer.add(cadena);
			}

		}

	}

	/**
	 * Etiqueta solamente los nodos que son hijos derechos de su padre con el valor
	 * de su posición en inorden, devolviendo el número de nodos que son hijos
	 * derechos.
	 *
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
	 * 10 | 5 | | 2 | | | ∅ | | | ∅ | | ∅ | 20 | | ∅ | | 30 | | | ∅ | | | ∅
	 * 
	 * devolverá 2 y el árbol quedaría etiquetado como: Solo etiqueta el 20 y el 30,
	 * porque son los únicos nodos que son hijos derechos {10, {5 , {2, ∅, ∅}, ∅},
	 * {20 [(inorder, 4)], ∅, {30 [(inorder, 5)], ∅, ∅}}}
	 * 
	 * @return numero de nodos hijos derechos
	 */
	public int tagRightChildrenInorder() {
		// TODO
		int[] pos = new int[1];
		int[] num = new int[1];
		pos[0] = 0;

		tagRightChildrenInorderWithrec(pos, num);
		return num[0];
	}

	private int tagRightChildrenInorderWithrec(int[] pos, int[] num) {

		if (isEmpty()) {
			return 0;
		} else {

			if (!getLeftBST().isEmpty()) {
				getLeftBST().tagRightChildrenInorderWithrec(pos, num);
			}

			pos[0]++;
			if (this.father != null && this.father.getRightBST() == this) {
				num[0]++;
				this.setTag("inorder", pos[0]);
			}

			if (!getRightBST().isEmpty()) {
				getRightBST().tagRightChildrenInorderWithrec(pos, num);
			}
		}

		return num[0];
	}

	/**
	 * Comprueba si el nodo actual (this) tiene un hermano en el mismo nivel, que
	 * tiene la misma estructura que él
	 *
	 * return true si su hermano tiene la misma estructura que él, false en caso
	 * contrario (si no tiene hermano o si lo tiene, este * no tiene la misma
	 * estructura que el)
	 */
	public boolean HasBrotherSameStruc() {
		// TODO

		if (this.father == null) {
			return false;
		} else {
			if (this.father.getLeftBST().isEmpty() || this.father.getRightBST().isEmpty()) {
				return false;
			} else {
				if (this.father.getLeftBST().content.equals(this.content)) {
					BinarySearchTreeImpl<T> brother = this.father.getRightBST();
					return this.HasBrotherSameStrucWithRec(brother);

				} else {
					BinarySearchTreeImpl<T> brother = this.father.getLeftBST();
					return this.HasBrotherSameStrucWithRec(brother);
				}
			}

		}

	}

	private boolean HasBrotherSameStrucWithRec(BinarySearchTreeImpl<T> brother) {

		if (!this.isEmpty() && brother.isEmpty()) {
			return false;

		} else if (this.isEmpty() && !brother.isEmpty()) {
			return false;
		} else if (this.isEmpty() && brother.isEmpty()) {
			return true;
		} else {
			return this.getLeftBST().HasBrotherSameStrucWithRec(brother.getLeftBST())
					&& this.getRightBST().HasBrotherSameStrucWithRec(brother.getRightBST());
		}

	}

	private void checkIsNull(T elem) {
		if (elem == null) {
			throw new IllegalArgumentException("ERROR: El elemento no puede ser null");
		}
	}

}
