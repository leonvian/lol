package lvc.com.languageofleo

import java.lang.IllegalArgumentException


sealed class Token {

    object AssignValueToken : Token()

    object CloseSentenceToken : Token()

    object OperatorSumToken : Token()

    object OperatorSubtractToken : Token()

    object OperatorMutiplyToken : Token()

    object OperatorDivisionToken : Token()

    object MethodToken : Token()

    object VariableToken : Token()

    class NameToken(val name: String) : Token()

    class LiteralStringToken(val literalValue: String) : Token()

    class LiteralDoubleToken(val literalValue: Double) : Token()

    class LiteralIntToken(val literalValue: Int) : Token()

    object IntTypeToken : Token()

    object StringTypeToken : Token()

    object DoubleTypeToken : Token()

    object OpenParenthesisToken : Token()

    object CloseParenthesisToken : Token()

    object OpenBlockToken : Token()

    object CloseBlockToken : Token()

    object IfToken : Token()

    object StartProgram : Token()

    object Print : Token()

    object StringBracket : Token()

    fun isCloseSentence() = this is CloseSentenceToken

    fun isLiteralValue() =
        this is LiteralIntToken || this is LiteralStringToken || this is LiteralDoubleToken

    fun isTokenName() = this is NameToken

    fun isOpenParenthesis() = this is OpenParenthesisToken

    fun isCloseParenthesis() = this is CloseParenthesisToken

    fun isMathOperator() = this is OperatorSumToken ||
            this is OperatorDivisionToken ||
            this is OperatorMutiplyToken ||
            this is OperatorSubtractToken

    fun getLiteralValue(): Any {
        return when (this) {
            is LiteralIntToken -> literalValue
            is LiteralStringToken -> literalValue
            is LiteralDoubleToken -> literalValue
            else -> throw InvalidTokenUsageException("Token $this is not a Literal Token")
        }
    }

    fun getLiteralType(): Type {
        return when (this) {
            is LiteralIntToken -> Type.INT
            is LiteralStringToken -> Type.STRING
            is LiteralDoubleToken -> Type.DOUBLE
            else -> throw InvalidTokenUsageException("Token $this is not a Literal Token")
        }
    }

    fun isStringBracket() = this is StringBracket

    fun getType(): Type {
        return when (this) {
            is IntTypeToken -> Type.INT
            is StringTypeToken -> Type.STRING
            is DoubleTypeToken -> Type.DOUBLE
            else -> throw InvalidTokenUsageException("Token $this is not a type token")
        }
    }

    fun getNameAttr(): String {
        return if (this is NameToken) {
            this.name
        } else {
            throw InvalidTokenUsageException("Token $this is not a Name token")
        }
    }


}