import kotlin.math.max

class AvlTree<T> {
    private class Node<T>(
        var data: T,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
    ) {
        val key: Int get() = data.hashCode()
        val min: Node<T> get() = left?.min ?: this
        val max: Node<T> get() = right?.max ?: this
        val isLeaf: Boolean get() = left == null && right == null
        var height: Int = 0
        val lHeight: Int get() = left?.height ?: -1
        val rHeight: Int get() = right?.height ?: -1

        fun link(toLink: T): Node<T> {
            when {
                key > toLink.key -> left = left?.link(toLink) ?: Node(toLink)
                key < toLink.key -> right = right?.link(toLink) ?: Node(toLink)
            }
            return this.balanced()
        }

        fun erase(toErase: T): Node<T>? {
            when {
                key < toErase.key -> right = right?.erase(toErase)
                key > toErase.key -> left = left?.erase(toErase)
                key == toErase.key -> {
                    if (this.isLeaf) return null
                    else if (left == null) {
                        data = right!!.min.data
                        right = right?.erase(data)
                    } else { // right == null or both != null
                        data = left!!.max.data
                        left = left?.erase(data)
                    }
                }
            }
            return this.balanced()
        }

        fun balanced(): Node<T> {
            var newRoot: Node<T> = this
            when (lHeight - rHeight) { // balance
                2 -> {
                    if (left!!.lHeight < left!!.rHeight) {
                        newRoot = left!!.right!!
                        left!!.right = null
                        newRoot.min.left = this.left
                        height = newRoot.height
                    } else {
                        newRoot = left!!
                    }
                    newRoot.max.right = this
                    this.left!!.height--
                    this.left = null
                }

                -2 -> {
                    if (right!!.rHeight < right!!.lHeight) {
                        newRoot = right!!.left!!
                        right!!.left = null
                        newRoot.max.right = this.right
                        height = newRoot.height
                    } else {
                        newRoot = right!!
                    }
                    newRoot.min.left = this
                    this.right!!.height--
                    this.right = null
                }
            }
            with(newRoot) { height = max(lHeight, rHeight) + 1 }
            return newRoot
        }

        fun accessSymmetric(action: (T) -> Unit) {
            left?.accessSymmetric(action)
            action(data)
            right?.accessSymmetric(action)
        }

        fun accessStraight(action: (T) -> Unit) {
            action(data)
            left?.accessStraight(action)
            right?.accessStraight(action)
        }

        fun accessReversed(action: (T) -> Unit) {
            left?.accessReversed(action)
            right?.accessReversed(action)
            action(data)
        }

        fun accessBroad(action: (T) -> Unit) {
            val nodes = ArrayDeque<Node<T>>()
            nodes.add(this)
            while (nodes.isNotEmpty()) {
                val node = nodes.first()
                node.left?.let { nodes.addLast(it) }
                node.right?.let { nodes.addLast(it) }
                nodes.removeFirst().also { action(it.data) }
            }
        }
    }

    constructor()
    constructor(array: Array<T>) {
        for (i: T in array) insert(i)
    }

    private var root: Node<T>? = null
    val height: Int get() = root?.height ?: -1

    fun insert(obj: T) {
        root = root?.link(obj) ?: Node(obj)
    }

    fun remove(obj: T) {
        root = root?.erase(obj)
    }

    fun accessSymmetric(action: (T) -> Unit) {
        root?.accessSymmetric(action)
    }

    fun accessStraight(action: (T) -> Unit) {
        root?.accessStraight(action)
    }

    fun accessReversed(action: (T) -> Unit) {
        root?.accessReversed(action)
    }

    fun accessBroad(action: (T) -> Unit) {
        root?.accessBroad(action)
    }
}

val Any?.key: Int get() = this.hashCode()