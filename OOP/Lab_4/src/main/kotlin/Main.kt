fun main(args: Array<String>) {
    AvlTree(arrayOf(6, 1, 3, 9, 4, 7, 2, 5, 8)).apply {
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
    }

}