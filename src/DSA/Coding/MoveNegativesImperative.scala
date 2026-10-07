package DSA.Coding

object MoveNegativesImperative {
  def separateNegativeAndPositive(arr: Array[Int]): Unit = {
    var left = 0
    var right = arr.length - 1

    // A while loop works identically to Java
    while (left <= right) {

      // arr(index) is how Scala accesses array elements (using parentheses, not square brackets)
      if (arr(left) < 0 && arr(right) < 0) {
        left += 1 // Scala does not support left++
      }
      else if (arr(left) >= 0 && arr(right) < 0) {
        // Swapping elements manually
        val temp = arr(left)
        arr(left) = arr(right)
        arr(right) = temp
        left += 1
        right -= 1
      }
      else if (arr(left) >= 0 && arr(right) >= 0) {
        right -= 1
      }
      else {
        left += 1
        right -= 1
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(-12, 11, -13, -5, 6, -7, 5, -3, -6)
    separateNegativeAndPositive(arr)

    println("Imperative Approach Result:")
    // mkString converts the array into a readable string separated by a space
    println(arr.mkString(" "))
  }
}