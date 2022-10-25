package lvc.com.languageofleo

import lvc.com.languageofleo.utils.createToken
import lvc.com.languageofleo.utils.isReservedWordNotSpacedRequire
import lvc.com.languageofleo.utils.isWord

object SyntaxValidation {

    fun run(currentToken: Token, code: String, startIndex: Int) {
        when {
            currentToken.isVariableToken() -> {
                val nextToken = createNextToken(code, startIndex)
                if (!nextToken.isTypeToken()) {
                    throw SyntaxException("Expecting TYPE token after found variable token")
                }
            }
            // TODO provide validations for all tokens
        }

    }

    private fun createNextToken(code: String, startIndex: Int): Token {
        var start = startIndex
        var end = startIndex

        while (start < code.length) {
            val possibleToken = code.substring(IntRange(start, end))
            if (possibleToken.trim().isEmpty()) {
                start ++
                end ++
                continue
            }

            if (possibleToken.isWord() || possibleToken.isReservedWordNotSpacedRequire()) {
                return createToken(possibleToken)
            }  else {
                end ++
            }
        }

        return createToken(code.substring(start, end))
    }

}