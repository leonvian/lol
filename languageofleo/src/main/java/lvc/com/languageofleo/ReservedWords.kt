package lvc.com.languageofleo


enum class ReservedWords(val word: String) {

    IF("if"),
    INT_TYPE("integer"),
    STRING_TYPE("string"),
    DOUBLE_TYPE("double"),
    METHOD("method"),
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    OPEN_BLOCK("{"),
    CLOSE_BLOCK("}"),
    CLOSE_LINE(";"),
    ASSIGN_SYMBOL_LINE("="),
    VARIABLE("variable"),
    START_PROGRAM("startProgram"),
    PRINT("print"),
    OPERATOR_SUM("+"),
    OPERATOR_SUBTRACT("-"),
    OPERATOR_MUlTIPLY("*"),
    STRING_BRACKET("\""),
    OPERATOR_DIVISION("/");


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

        fun getReservedWordOn(target: String): ReservedWords? {
            values().forEach {
                if (target.contains(it.word))
                    return it
            }

            return null
        }

    }

}