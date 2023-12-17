fun main(args: Array<String>) {
//    AvlTree(arrayOf(6, 1, 3, 9, 4, 7, 2, 5, 8)).apply {
    AvlTree(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)).apply {
//    AvlTree(arrayOf(15, 7, 30, 3, 11, 1, 4)).apply {
//    AvlTree(arrayOf(15, 7, 30, 3, 11, 8, 12)).apply {
        println("Tree height: $height")
        remove(3)
        remove(2)
        remove(6)
        print("\nTree elements (symmetric): ")
        accessSymmetric { data -> print("$data  ") }
        print("\nTree elements (straight): ")
        accessStraight { data -> print("$data  ") }
        print("\nTree elements (reversed): ")
        accessReversed { data -> print("$data  ") }
        insert(3)
        print("\nTree elements (broad): ")
        accessBroad { data -> print("$data  ") }
    }
}