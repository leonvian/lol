package lvc.com.languageofleo

import java.lang.IllegalArgumentException


sealed class Token {

    object AssignValueToken : Token()  {
        override fun toString(): String {
            return "AssignValueToken"
        }
    }

    object CloseSentenceToken : Token()  {
        override fun toString(): String {
            return "CloseSentenceToken"
        }
    }

    object OperatorSumToken : Token()  {
        override fun toString(): String {
            return "OperatorSumToken"
        }
    }

    object OperatorSubtractToken : Token()  {
        override fun toString(): String {
            return "OperatorSubtractToken"
        }
    }

    object OperatorMutiplyToken : Token()  {
        override fun toString(): String {
            return "OperatorMutiplyToken"
        }
    }

    object OperatorDivisionToken : Token()  {
        override fun toString(): String {
            return "OperatorDivisionToken"
        }
    }

    object MethodToken : Token()  {
        override fun toString(): String {
            return "MethodToken"
        }
    }

    object VariableToken : Token() {
        override fun toString(): String {
            return "VariableToken"
        }
    }

    class NameToken(val name: String) : Token() {
        override fun toString(): String {
            return "NameToken $name"
        }
    }

    class LiteralStringToken(val literalValue: String) : Token() {
        override fun toString(): String {
            return "LiteralStringToken $literalValue"
        }
    }

    class LiteralDoubleToken(val literalValue: Double) : Token() {
        override fun toString(): String {
            return "LiteralDoubleToken $literalValue"
        }
    }

    class LiteralIntToken(val literalValue: Int) : Token() {
        override fun toString(): String {
            return "LiteralIntToken $literalValue"
        }
    }

    object IntTypeToken : Token() {
        override fun toString(): String {
            return "IntTypeToken"
        }
    }

    object StringTypeToken : Token() {
        override fun toString(): String {
            return "StringTypeToken"
        }
    }

    object DoubleTypeToken : Token() {
        override fun toString(): String {
            return "DoubleTypeToken"
        }
    }

    object OpenParenthesisToken : Token() {
        override fun toString(): String {
            return "OpenParenthesisToken"
        }
    }

    object CloseParenthesisToken : Token() {
        override fun toString(): String {
            return "CloseParenthesisToken"
        }
    }

    object OpenBlockToken : Token() {
        override fun toString(): String {
            return "OpenBlockToken"
        }
    }

    object CloseBlockToken : Token() {
        override fun toString(): String {
            return "CloseBlockToken"
        }
    }

    object IfToken : Token() {
        override fun toString(): String {
            return "IfToken"
        }
    }

    object StartProgram : Token() {
        override fun toString(): String {
            return "Start Program"
        }
    }

    object Print : Token() {
        override fun toString(): String {
            return "Print"
        }
    }

    object StringBracket : Token()  {
        override fun toString(): String {
            return "StringBracket"
        }
    }

    fun isCloseSentence() = this is CloseSentenceToken

    fun isLiteralValue() =
        this is LiteralIntToken || this is LiteralStringToken || this is LiteralDoubleToken

    fun isLiteralString() = this is LiteralStringToken

    fun isTokenName() = this is NameToken

    fun isOpenParenthesis() = this is OpenParenthesisToken

    fun isCloseParenthesis() = this is CloseParenthesisToken

    fun isVariableToken() = this is VariableToken

    fun isTypeToken() = this is IntTypeToken || this is StringTypeToken || this is DoubleTypeToken

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