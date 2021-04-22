package lvc.com.languageofleo

class TokenGenerator {

    private var previous: Token? = null

    fun generateTokens(code: String): List<Token> {
        val tokens = arrayListOf<Token>()
        code.replace("\""," \" ")
            .trim()
            .split(" ")
            .map { it.trim() }
            .filter {
                it.isNotBlank()
            }
            .forEach {
                tokens.addAll(createTokensByWord(it))
            }

        return tokens
    }

    private fun createTokensByWord(rawValue: String): List<Token> {
        val tokens = arrayListOf<Token>()
        if (isSimpleToken(rawValue)) {
            tokens.add(createSimpleToken(rawValue))
        } else {
            tokens.addAll(createComplexToken(rawValue))
        }

        return tokens
    }

    private fun createSimpleToken(rawValue: String): Token {
        if (ReservedWords.isReservedWord(rawValue)) {
            val newToken = when (rawValue) {
                ReservedWords.IF.word -> Token.IfToken
                ReservedWords.ASSIGN_SYMBOL_LINE.word -> Token.AssignValueToken
                ReservedWords.METHOD.word -> Token.MethodToken
                ReservedWords.VARIABLE.word -> Token.VariableToken
                ReservedWords.INT_TYPE.word -> Token.IntTypeToken
                ReservedWords.DOUBLE_TYPE.word -> Token.DoubleTypeToken
                ReservedWords.STRING_TYPE.word -> Token.StringTypeToken
                ReservedWords.CLOSE_LINE.word -> Token.CloseSentenceToken
                ReservedWords.OPEN_BLOCK.word -> Token.OpenBlockToken
                ReservedWords.CLOSE_BLOCK.word -> Token.CloseBlockToken
                ReservedWords.OPEN_PARENTHESIS.word -> Token.OpenParenthesisToken
                ReservedWords.CLOSE_PARENTHESIS.word -> Token.CloseParenthesisToken
                ReservedWords.START_PROGRAM.word -> Token.StartProgram
                ReservedWords.OPERATOR_SUM.word -> Token.OperatorSumToken
                ReservedWords.OPERATOR_SUBTRACT.word -> Token.OperatorSubtractToken
                ReservedWords.OPERATOR_MUlTIPLY.word -> Token.OperatorMutiplyToken
                ReservedWords.OPERATOR_DIVISION.word -> Token.OperatorDivisionToken
                ReservedWords.PRINT.word -> Token.Print
                ReservedWords.STRING_BRACKET.word -> Token.StringBracket
                else -> throw IllegalArgumentException("Unrecognized token $rawValue")
            }

            previous = newToken
            return newToken
        }

        if (!ReservedWords.containReservedWord(rawValue)) {
            val newToken = when {
                isPreviousTokenStringBracket() -> Token.LiteralStringToken(rawValue)
                isALiteralInt(rawValue) -> Token.LiteralIntToken(rawValue.toInt())
                isALiteralDouble(rawValue) -> Token.LiteralDoubleToken(rawValue.toDouble())
                else -> Token.NameToken(rawValue)
            }

            previous = newToken
            return newToken
        }

        throw IllegalArgumentException("Not a simple token $rawValue")
    }

    private fun isPreviousTokenStringBracket() = previous?.isStringBracket() ?: false

    private fun isSimpleToken(rawValue: String) =
        ReservedWords.isReservedWord(rawValue) || !ReservedWords.containReservedWord(rawValue)

    private fun createComplexToken(rawValue: String): List<Token> =
        splitByReserverdWords(rawValue).map { createSimpleToken(it) }

    private fun splitByReserverdWords(rawValue: String): List<String> {
        val patternAllReservedWords = ReservedWords.values()
            .map { it.word }
            .toTypedArray()

        val literals = rawValue
            .split(*patternAllReservedWords)
            .filter { it.isNotEmpty() && it.isNotBlank() }
            .toMutableList()

        val reservedWords = patternAllReservedWords.filter {
            rawValue.contains(it)
        }.sortedBy {
            rawValue.indexOf(it)
        }.toMutableList()

        val tokens = arrayListOf<String>()
        while (literals.isNotEmpty() || reservedWords.isNotEmpty()) {
            when {
                literals.isEmpty() -> {
                    tokens.addAll(reservedWords)
                    reservedWords.clear()
                }
                reservedWords.isEmpty() -> {
                    tokens.addAll(literals)
                    literals.clear()
                }
                rawValue.indexOf(literals.first()) < rawValue.indexOf(reservedWords.first()) -> {
                    tokens.add(literals.pullFirst())
                }
                else -> {
                    tokens.add(reservedWords.pullFirst())
                }
            }
        }

        return tokens
    }

    private fun isALiteralInt(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isALiteralDouble(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isALiteralString(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun MutableList<String>.pullFirst(): String {
        val result = first()
        removeAt(0)
        return result
    }

}