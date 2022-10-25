package lvc.com.languageofleo.utils

import lvc.com.languageofleo.ReservedWords
import lvc.com.languageofleo.SyntaxException
import lvc.com.languageofleo.Token

fun createLiteralStringToken(code: String, startPos: Int): Pair<Token, Int> {
    var start = startPos
    val stringBuilder = StringBuilder()
    while (start < code.length) {
        val currentChar = code[start]
        if (currentChar.toString() == ReservedWords.STRING_BRACKET.word) {
            stringBuilder.deleteCharAt(0)
            stringBuilder.deleteCharAt(stringBuilder.length -1)
            return Pair(Token.LiteralStringToken(stringBuilder.toString()), start)
        } else {
            stringBuilder.append(currentChar)
        }
        start++
    }
    throw SyntaxException("String with no closing bracket $code")
}

fun String.isReservedWordNotSpacedRequire(): Boolean {
    ReservedWords.getNotSpacedReservedWords().forEach {
        if (it.word == this) {
            return true
        }
    }

    return false
}

fun String.containsReservedWordNotSpacedRequire(): Boolean {
    ReservedWords.getNotSpacedReservedWords().forEach {
        if (this.contains(it.word)) {
            return true
        }
    }

    return false
}

fun String.getReservedWordNotSpacedRequire(): ReservedWords {
    ReservedWords.getNotSpacedReservedWords().forEach {
        if (this.contains(it.word)) {
            return it
        }
    }

    throw IllegalArgumentException()
}

fun String.isStringBracket(): Boolean = this == ReservedWords.STRING_BRACKET.word

fun String.isWord() = last() == ' '

fun createToken(rawValueS: String): Token {
    val rawValue = rawValueS.trim()
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
            ReservedWords.OPERATOR_MUlTIPLICATION.word -> Token.OperatorMutiplyToken
            ReservedWords.OPERATOR_DIVISION.word -> Token.OperatorDivisionToken
            ReservedWords.PRINT.word -> Token.Print
            ReservedWords.STRING_BRACKET.word -> Token.StringBracket
            else -> throw IllegalArgumentException("Unrecognized token $rawValue")
        }

        return newToken
    }

    if (!ReservedWords.containReservedWord(rawValue)) {
        val newToken = when {
            // shouldCreateStringToken() -> Token.LiteralStringToken(rawValue)
            isALiteralInt(rawValue) -> Token.LiteralIntToken(rawValue.toInt())
            isALiteralDouble(rawValue) -> Token.LiteralDoubleToken(rawValue.toDouble())
            else -> Token.NameToken(rawValue)
        }

        return newToken
    }

    throw IllegalArgumentException("Not a simple token $rawValue")
}

fun isALiteralInt(value: String): Boolean {
    return try {
        value.toInt()
        true
    } catch (e: Exception) {
        false
    }
}

fun isALiteralDouble(value: String): Boolean {
    return try {
        value.toDouble()
        true
    } catch (e: Exception) {
        false
    }
}

fun isALiteralString(value: String): Boolean {
    return try {
        value.toDouble()
        true
    } catch (e: Exception) {
        false
    }
}

fun MutableList<String>.pullFirst(): String {
    val result = first()
    removeAt(0)
    return result
}