package signature

import java.io.File
import java.util.*

val letters = mapOf<String, List<String>>(
        ("a" to listOf("____", "|__|", "|  |")),
        ("b" to listOf("___ ", "|__]", "|__]")),
        ("c" to listOf("____", "|   ", "|___")),
        ("d" to listOf("___ ", "|  \\", "|__/")),
        ("e" to listOf("____", "|___", "|___")),
        ("f" to listOf("____", "|___", "|   ")),
        ("g" to listOf("____", "| __", "|__]")),
        ("h" to listOf("_  _", "|__|", "|  |")),
        ("i" to listOf("_", "|", "|")),
        ("j" to listOf(" _", " |", "_|")),
        ("k" to listOf("_  _", "|_/ ", "| \\_")),
        ("l" to listOf("_   ", "|   ", "|___")),
        ("m" to listOf("_  _", "|\\/|", "|  |")),
        ("n" to listOf("_  _", "|\\ |", "| \\|")),
        ("o" to listOf("____", "|  |", "|__|")),
        ("p" to listOf("___ ", "|__]", "|   ")),
        ("q" to listOf("____", "|  |", "|_\\|")),
        ("r" to listOf("____", "|__/", "|  \\")),
        ("s" to listOf("____", "[__ ", "___]")),
        ("t" to listOf("___", " | ", " | ")),
        ("u" to listOf("_  _", "|  |", "|__|")),
        ("v" to listOf("_  _", "|  |", " \\/ ")),
        ("w" to listOf("_ _ _", "| | |", "|_|_|")),
        ("x" to listOf("_  _", " \\/ ", "_/\\_")),
        ("y" to listOf("_   _", " \\_/ ", "  |  ")),
        ("z" to listOf("___ ", "  / ", " /__")),
        (" " to listOf("    ", "    ", "    "))
)

fun readFont(): Map<Char, List<String>>{
    val scn = Scanner(File("C:\\Users\\Иван\\IdeaProjects\\ASCII Text Signature\\ASCII Text Signature\\task\\src\\signature\\roman.txt"))
    val font = mutableMapOf<Char, List<String>>()
    // Skip first line
    scn.nextLine()
    for (char in 'a'..'z') {
        // Skip one line
        val dataLine = scn.nextLine()
        // Scanning 10 next lines
        val letter = mutableMapOf<Int, String>()
        for (q in 1..10) letter[q-1] = scn.nextLine()
        font[char] = letter.values.toList()
    }
    for (char in 'A'..'Z') {
        // Skip one line
        val dataLine = scn.nextLine()
        // Scanning 10 next lines
        val letter = mutableMapOf<Int, String>()
        for (q in 1..10) letter[q-1] = scn.nextLine()
        font[char] = letter.values.toList()
    }
    val letter = arrayListOf<String>()
    for (i in 0..9) letter.add(" ".repeat(10))
    font[' '] = letter
    return font

}

fun main(){
    val scanner = Scanner(System.`in`)
    val font = readFont()
    print("Enter name and surname: ")
    val name = scanner.nextLine()
    print("Enter person's status: ")
    val status = scanner.nextLine().toLowerCase()

    val result = mutableMapOf<Int, String>()
    val nameArray = mutableListOf<String>("", "", "", "", "", "", "", "", "", "")
    for (char in name) {
        for (line in 0..9) {
            nameArray[line] += "${font[char]!![line]}"
        }
    }
    val statusArray = mutableListOf<String>("", "", "")
    for (line in 0..2) {
        var result = ""
        for (char in status) {
            var letter = letters[char.toString()]?.get(line)
            result += "${letter} "
        }
        statusArray[line] = result
    }
    val nameLength = nameArray[0].length
    val statusLength = statusArray[0].length
    if (nameLength > statusLength) {
        result[0] = "8".repeat(nameLength + 8)
        for (i in 0..9) result[i+1] = "88  ${nameArray[i]}  88"
        var spacesBefore = (nameLength + 4 - statusLength)/2
        var spacesAfter = (nameLength + 4) - (spacesBefore + 2 + statusLength) + 2
        for (i in 0..2) {
            result[i + 11] = "88" + " ".repeat(spacesBefore) + statusArray[i]
            result[i + 11] += " ".repeat(spacesAfter) + "88"
        }
        result[14] = "8".repeat(nameLength + 8)
    } else {
        result[0] = "8".repeat(statusLength + 8)
        val spacesBefore = (statusLength + 4 - nameLength)/2
        val spacesAfter = (statusLength + 4) - (spacesBefore + 2 + nameLength) + 2
        for (i in 0..9) {
            result[i + 1] = "88" + " ".repeat(spacesBefore) + nameArray[i]
            result[i + 1] += " ".repeat(spacesAfter) + "88"
        }
        for (i in 0..2) result[i + 11] = "88  ${statusArray[i]}  88"
        result[14] = "8".repeat(statusLength + 8)
    }
    for (line in 0..14) {
        println(result[line])
    }
}
