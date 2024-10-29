package Lr4;

class BTreeNode {
    int[] keys; // Массив ключей в узле
    int t; // Минимальная степень дерева (определяет минимальное и максимальное количество ключей)
    BTreeNode[] children; // Массив дочерних узлов
    int n; // Количество ключей в узле
    boolean leaf; // true, если узел является листом

    // Конструктор для создания нового узла B-дерева
    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }

    // Метод для обхода узла
    public void traverse() {
        int i;
        for (i = 0; i < this.n; i++) {
            if (!this.leaf) {
                children[i].traverse(); // Обход дочерних узлов, если узел не является листом
            }
            System.out.print(" " + keys[i]); // Вывод ключа узла
        }
        if (!this.leaf) {
            children[i].traverse(); // Обход последнего дочернего узла
        }
    }

    // Метод для поиска ключа k в поддереве с корнем в этом узле
    public BTreeNode search(int k) {
        int i = 0;
        while (i < n && k > keys[i]) {
            i++; // Поиск подходящего индекса для ключа
        }
        if (i < n && keys[i] == k) {
            return this; // Возвращаем узел, если ключ найден
        }
        if (leaf) {
            return null; // Если узел - лист и ключ не найден, возвращаем null
        }
        return children[i].search(k); // Иначе выполняем поиск в соответствующем поддереве
    }

    // Вставка нового ключа в неполный узел
    public void insertNonFull(int k) {
        int i = n - 1;
        if (leaf) {
            // Вставляем ключ в лист, перемещая элементы вправо для вставки нового ключа
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = k;
            n = n + 1;
        } else {
            // Находим подходящего ребенка для вставки ключа
            while (i >= 0 && keys[i] > k) {
                i--;
            }
            // Проверяем, не переполнен ли найденный дочерний узел
            if (children[i + 1].n == 2 * t - 1) {
                splitChild(i + 1, children[i + 1]);
                if (keys[i + 1] < k) {
                    i++;
                }
            }
            children[i + 1].insertNonFull(k); // Рекурсивно вставляем ключ в подходящий дочерний узел
        }
    }

    // Разделение полного дочернего узла y на два узла
    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf); // Новый узел для хранения t-1 ключей y
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t]; // Перемещаем последние t-1 ключей в новый узел
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t]; // Перемещаем последних t дочерних узлов
            }
        }
        y.n = t - 1;
        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j]; // Сдвигаем дочерние узлы вправо
        }
        children[i + 1] = z; // Устанавливаем нового ребенка
        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j]; // Сдвигаем ключи вправо
        }
        keys[i] = y.keys[t - 1]; // Продвигаем средний ключ в текущий узел
        n = n + 1;
    }

    // Удаление ключа k из поддерева с корнем в этом узле
    public boolean delete(int k) {
        int idx = findKey(k);

        // Случай 1: ключ находится в текущем узле
        if (idx < n && keys[idx] == k) {
            if (leaf) {
                removeFromLeaf(idx); // Удаление из листа
            } else {
                removeFromNonLeaf(idx); // Удаление из внутреннего узла
            }
            return true;
        } else {
            // Случай 2: ключ отсутствует в текущем узле
            if (leaf) {
                return false; // Ключ отсутствует в дереве
            }
            boolean flag = (idx == n); // Флаг, если последний дочерний узел
            if (children[idx].n < t) {
                fill(idx); // Убедиться, что у ребенка достаточно ключей
            }
            if (flag && idx > n) {
                children[idx - 1].delete(k); // Рекурсивное удаление из предыдущего ребенка
            } else {
                children[idx].delete(k); // Рекурсивное удаление из текущего ребенка
            }
            return true;
        }
    }

    // Метод для поиска индекса ключа k в узле
    private int findKey(int k) {
        int idx = 0;
        while (idx < n && keys[idx] < k) {
            idx++;
        }
        return idx;
    }

    // Удаление ключа из листа
    private void removeFromLeaf(int idx) {
        for (int i = idx + 1; i < n; i++) {
            keys[i - 1] = keys[i];
        }
        n--;
    }

    // Удаление ключа из внутреннего узла
    private void removeFromNonLeaf(int idx) {
        int k = keys[idx];
        if (children[idx].n >= t) {
            int pred = getPred(idx);
            keys[idx] = pred;
            children[idx].delete(pred);
        } else if (children[idx + 1].n >= t) {
            int succ = getSucc(idx);
            keys[idx] = succ;
            children[idx + 1].delete(succ);
        } else {
            merge(idx);
            children[idx].delete(k);
        }
    }

    // Получение предшественника ключа в поддереве
    private int getPred(int idx) {
        BTreeNode cur = children[idx];
        while (!cur.leaf) {
            cur = cur.children[cur.n];
        }
        return cur.keys[cur.n - 1];
    }

    // Получение преемника ключа в поддереве
    private int getSucc(int idx) {
        BTreeNode cur = children[idx + 1];
        while (!cur.leaf) {
            cur = cur.children[0];
        }
        return cur.keys[0];
    }

    // Обеспечение достаточного количества ключей в дочернем узле
    private void fill(int idx) {
        if (idx != 0 && children[idx - 1].n >= t) {
            borrowFromPrev(idx);
        } else if (idx != n && children[idx + 1].n >= t) {
            borrowFromNext(idx);
        } else {
            if (idx != n) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }
    }

    // Заимствование ключа у предыдущего дочернего узла
    private void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];
        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = keys[idx - 1];
        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }
        keys[idx - 1] = sibling.keys[sibling.n - 1];
        child.n += 1;
        sibling.n -= 1;
    }

    // Заимствование ключа у следующего дочернего узла
    private void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];
        child.keys[child.n] = keys[idx];
        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }
        keys[idx] = sibling.keys[0];
        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }
        child.n += 1;
        sibling.n -= 1;
    }

    // Слияние дочернего узла с его соседом
    private void merge(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];
        child.keys[t - 1] = keys[idx];
        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + t] = sibling.keys[i];
        }
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + t] = sibling.children[i];
            }
        }
        for (int i = idx + 1; i < n; i++) {
            keys[i - 1] = keys[i];
        }
        for (int i = idx + 2; i <= n; i++) {
            children[i - 1] = children[i];
        }
        child.n += sibling.n + 1;
        n--;
    }
}