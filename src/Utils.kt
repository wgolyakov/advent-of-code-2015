import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Reads given input txt file.
 */
fun readFile(name: String) = File("src", "$name.txt").readText().trim()

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
