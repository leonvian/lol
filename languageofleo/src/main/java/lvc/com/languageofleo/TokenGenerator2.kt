package lvc.com.languageofleo

import lvc.com.languageofleo.utils.containsReservedWordNotSpacedRequire
import lvc.com.languageofleo.utils.createLiteralStringToken
import lvc.com.languageofleo.utils.createToken
import lvc.com.languageofleo.utils.getReservedWordNotSpacedRequire
import lvc.com.languageofleo.utils.isReservedWordNotSpacedRequire
import lvc.com.languageofleo.utils.isStringBracket
import lvc.com.languageofleo.utils.isWord

class TokenGenerator2 {

    /*
                    "  variable integer x = 3;\n" +
                    "  variable integer y = 5;\n" +
                    "   startProgram {\n" +
                    "     variable integer z = x + y;\n" +
                    "     print(z);\n" +
                    "  }"
     */

    fun generateTokens(codeInput: String): List<Token> {
        val code = CodePreProcess.process(codeInput)

        val tokens = arrayListOf<Token>()
        var start = 0
        var end = 0
        var openString = false

        while (end < code.length) {
            val possibleToken = code.substring(IntRange(start, end))

            when {
                possibleToken.trim().isEmpty() -> {
                    start++
                    end++
                }
                possibleToken.isStringBracket() -> {
                    val token = createToken(possibleToken)
                    tokens.add(token)
                    end++
                    start = end
                    SyntaxValidation.run(token, code, start)

                    if (openString) {
                        openString = false
                    } else {
                        openString = true
                        val literalStringResult = createLiteralStringToken(code, start)
                        tokens.add(literalStringResult.first)
                        start = literalStringResult.second
                        end = start
                    }
                }
                !possibleToken.isWord() -> {
                    end ++
                }
                else -> {
                    val token = createToken(possibleToken)
                    tokens.add(token)
                    end++
                    start = end
                    SyntaxValidation.run(token, code, start)
                }
            }
        }

        return tokens
    }


}