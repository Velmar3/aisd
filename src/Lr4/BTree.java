package Lr4;

class BTree {
    BTreeNode root; // Корневой узел B-дерева
    int t; // Минимальная степень дерева (определяет максимальное и минимальное количество ключей)

    // Конструктор, создающий пустое B-дерево с заданной минимальной степенью t
    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    // Метод для обхода дерева
    public void traverse() {
        if (root != null) {
            root.traverse(); // Если корень не пустой, обойти его
        }
    }

    // Метод для поиска ключа k в дереве
    // Возвращает узел, содержащий ключ, или null, если ключ не найден
    public BTreeNode search(int k) {
        if (root == null) {
            return null; // Если дерево пустое, возвращает null
        } else {
            return root.search(k); // Иначе вызывает поиск в корневом узле
        }
    }

    // Метод для вставки нового ключа k в дерево
    public void insert(int k) {
        if (root == null) {
            // Если корень пустой, создается новый узел, и ключ добавляется в него
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            // Если корень заполнен, то его нужно разделить перед вставкой
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false); // Новый узел, который станет новым корнем
                s.children[0] = root;
                s.splitChild(0, root); // Разделить старый корень
                int i = 0;
                if (s.keys[0] < k) {
                    i++; // Определяем, в какой части нового корня вставлять ключ
                }
                s.children[i].insertNonFull(k); // Вставляем ключ в неполный узел
                root = s; // Новый узел становится корнем дерева
            } else {
                root.insertNonFull(k); // Если корень не полный, вставляем ключ напрямую
            }
        }
    }

    // Метод для удаления ключа k из дерева
    // Возвращает true, если ключ был удален, и false, если ключ не найден
    public boolean delete(int k) {
        if (root == null) {
            System.out.println("Дерево пустое.");
            return false;
        }
        boolean deleted = root.delete(k); // Удаление ключа в корне
        if (root.n == 0) { // Проверка, если после удаления корень пуст
            root = root.leaf ? null : root.children[0]; // Если корень пуст и не является листом, заменяем его первым ребенком
        }
        return deleted;
    }
}
