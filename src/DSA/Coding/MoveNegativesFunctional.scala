package DSA.Coding

object MoveNegativesFunctional {
  def main(args: Array[String]): Unit = {
    val list = List(-12, 11, -13, -5, 6, -7, 5, -3, -6)

    // partition takes a lambda function (condition).
    // It returns a pair of lists: (those that match the condition, those that don't)
    // Here, '_' represents each element in the list.
    val (negatives, positives) = list.partition(_ < 0)

    // We combine the two lists using the ':::' operator, which concatenates two lists
    val rearrangedList = negatives ::: positives

    println("Functional Approach Result:")
    println(rearrangedList)
  }
}