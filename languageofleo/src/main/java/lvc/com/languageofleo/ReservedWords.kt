package lvc.com.languageofleo


/**
 *
 * Need space is true if the reserved word needs to be around spaces to be valid.
 *
 * example:
 * variable string name="Leonardo"
 *
 * The reserved words variable, string needs to have space
 * On the other hand = and "" doesn't need
 */
enum class ReservedWords(
    val word: String,
    val needSpace: Boolean = true
) {

    IF("if"),
    INT_TYPE("integer"),
    STRING_TYPE("string"),
    DOUBLE_TYPE("double"),
    METHOD("method"),
    OPEN_PARENTHESIS("(", false),
    CLOSE_PARENTHESIS(")", false),
    OPEN_BLOCK("{", false),
    CLOSE_BLOCK("}", false),
    CLOSE_LINE(";", false),
    ASSIGN_SYMBOL_LINE("=", false),
    VARIABLE("variable"),
    START_PROGRAM("startProgram"),
    PRINT("print"),
    OPERATOR_SUM("+", false),
    OPERATOR_SUBTRACT("-", false),
    OPERATOR_MUlTIPLICATION("*", false),
    STRING_BRACKET("\"", false),
    OPERATOR_DIVISION("/", false);

    companion object {

        fun isReservedWord(target: String): Boolean {
            values().forEach {
                if (it.word == target)
                    return true
            }

            return false
        }

        fun containReservedWord(target: String): Boolean {
            values().forEach {
                if (target.contains(it.word))
                    return true
            }

            return false
        }


        fun getSpacedReservedWords(): List<ReservedWords> = values().filter {
            it.needSpace
        }

        fun getNotSpacedReservedWords(): List<ReservedWords> = values().filter {
            !it.needSpace
        }
    }

}