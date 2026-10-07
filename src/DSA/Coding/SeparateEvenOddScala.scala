package DSA.Coding

object SeparateEvenOddScala {
  def main(args: Array[String]): Unit = {
    val list = List(12, 34, 45, 9, 8, 90, 3)

    // partition splits the list: 
    // Left side gets elements where element % 2 == 0 (Evens)
    // Right side gets the rest (Odds)
    val (evens, odds) = list.partition(num => num % 2 == 0)

    // Concatenate evens and odds together
    val result = evens ::: odds

    println("Evens and Odds Separated:")
    println(result)
  }
}