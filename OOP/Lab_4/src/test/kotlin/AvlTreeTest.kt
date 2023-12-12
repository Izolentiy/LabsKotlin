import org.junit.jupiter.api.Assertions.*

class AvlTreeTest {
    private var tree = AvlTree<Int>()
    fun setUp() {
        tree = AvlTree()
    }

    fun `remove node, empty tree`() {}
    fun `remove root, one element tree`() {}
    fun `remove root, few element tree`() {}
    fun `remove leaf, regular tree`() {}

    fun `insert node`() {}
}