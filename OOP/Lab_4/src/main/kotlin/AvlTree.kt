import kotlin.math.abs
import kotlin.math.max

class AvlTree<T> {
    private class Node<T>(
        var data: T,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
    ) {
        val key get() = data.hashCode()
        val min: Node<T> get() = left?.min ?: this
        val max: Node<T> get() = right?.max ?: this
        val isLeaf: Boolean
            get() = left == null && right == null
        val height: Int
            get() {
                val lHeight = left?.height ?: -1
                val rHeight = right?.height ?: -1
                return 1 + max(lHeight, rHeight)
            }

        fun delta(other: Node<T>): UInt {
            return when {
                key >= 0 && other.key <= 0 -> key.toUInt() + (-other.key).toUInt()
                key <= 0 && other.key >= 0 -> (-key).toUInt() + other.key.toUInt()
                else -> abs(key - other.key).toUInt()
            }
        }

        fun link(toLink: T): Node<T> {
            when {
                key > toLink.key -> left = left?.link(toLink) ?: Node(toLink)
                key < toLink.key -> right = right?.link(toLink) ?: Node(toLink)
            }
            return this
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
                    } else if (right == null) {
                        data = left!!.max.data
                        left = left?.erase(data)
                    } else when {
                        delta(right!!.min) < delta(left!!.max) -> {
                            data = right!!.min.data
                            right = right?.erase(data)
                        }

                        delta(right!!.min) >= delta(left!!.max) -> {
                            data = left!!.max.data
                            left = left?.erase(data)
                        }
                    }

                }
            }
            return this
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


}

val Any?.key: Int get() = this.hashCode()