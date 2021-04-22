package lvc.com.languageofleo

import org.junit.Assert
import org.junit.Test

class TokenGeneratorTest {

    @Test
    fun `test generate tokens sum`() {
        val CODE_TEST =
                    "  variable integer x = 3;\n" +
                    "  variable integer y = 5;\n" +
                    "   startProgram {\n" +
                    "     variable integer z = x + y;\n" +
                    "     print(z);\n" +
                    "  }"

        val tokenGenerator = TokenGenerator()
        val tokens = tokenGenerator.generateTokens(CODE_TEST)

        assert(tokens[0] is Token.VariableToken)
        assert(tokens[1] is Token.IntTypeToken)
        assert(tokens[2] is Token.NameToken)
        assert((tokens[2] as Token.NameToken).name == "x")
        assert(tokens[3] is Token.AssignValueToken)
        assert(tokens[4] is Token.LiteralIntToken)
        assert(tokens[5] is Token.CloseSentenceToken)

        assert(tokens[6] is Token.VariableToken)
        assert(tokens[7] is Token.IntTypeToken)
        assert(tokens[8] is Token.NameToken)
        assert((tokens[8] as Token.NameToken).name == "y")
        assert(tokens[9] is Token.AssignValueToken)
        assert(tokens[10] is Token.LiteralIntToken)
        assert(tokens[11] is Token.CloseSentenceToken)

        assert(tokens[12] is Token.StartProgram)
        assert(tokens[13] is Token.OpenBlockToken)

        assert(tokens[14] is Token.VariableToken)
        assert(tokens[15] is Token.IntTypeToken)
        assert(tokens[16] is Token.NameToken)
        assert((tokens[16] as Token.NameToken).name == "z")
        assert(tokens[17] is Token.AssignValueToken)
        assert(tokens[18] is Token.NameToken)
        assert(tokens[19] is Token.OperatorSumToken)
        assert(tokens[20] is Token.NameToken)
        assert(tokens[21] is Token.CloseSentenceToken)
        assert(tokens[22] is Token.Print)
        assert(tokens[23] is Token.OpenParenthesisToken)
        assert(tokens[24] is Token.NameToken)
        assert(tokens[25] is Token.CloseParenthesisToken)
        assert(tokens[26] is Token.CloseSentenceToken)

        assert(tokens[27] is Token.CloseBlockToken)
    }

    @Test
    fun `test generate tokens variable string`() {
        val CODE_TEST = "variable string y = \"leonardo\";"

        val tokenGenerator = TokenGenerator()
        val tokens = tokenGenerator.generateTokens(CODE_TEST)

        Assert.assertEquals(8, tokens.size )

        assert(tokens[0] is Token.VariableToken)

        assert(tokens[1] is Token.StringTypeToken)

        assert(tokens[2] is Token.NameToken)
        assert((tokens[2] as Token.NameToken).name == "y")

        assert(tokens[3] is Token.AssignValueToken)

        assert(tokens[4] is Token.StringBracket)

        assert(tokens[5] is Token.LiteralStringToken)
        assert((tokens[5] as Token.LiteralStringToken).literalValue == "leonardo")

        assert(tokens[6] is Token.StringBracket)

        assert(tokens[7] is Token.CloseSentenceToken)
    }

    @Test
    fun `test generate tokens variable string long`() {
        val CODE_TEST = "variable string y = \"leonardo viana casasanta\";"

        val tokenGenerator = TokenGenerator()
        val tokens = tokenGenerator.generateTokens(CODE_TEST)

        Assert.assertEquals(10, tokens.size )

        assert(tokens[0] is Token.VariableToken)

        assert(tokens[1] is Token.StringTypeToken)

        assert(tokens[2] is Token.NameToken)
        assert((tokens[2] as Token.NameToken).name == "y")

        assert(tokens[3] is Token.AssignValueToken)

        assert(tokens[4] is Token.StringBracket)

        assert(tokens[5] is Token.LiteralStringToken)
        assert((tokens[5] as Token.LiteralStringToken).literalValue == "leonardo")

        assert(tokens[6] is Token.LiteralStringToken)
        assert((tokens[6] as Token.LiteralStringToken).literalValue == "viana")

        assert(tokens[7] is Token.LiteralStringToken)
        assert((tokens[7] as Token.LiteralStringToken).literalValue == "casasanta")

        assert(tokens[8] is Token.StringBracket)

        assert(tokens[9] is Token.CloseSentenceToken)
    }

    @Test
    fun `test generate tokens for assign variable int`() {
        val testCode = "variable integer x=10;"
        val tokenGenerator = TokenGenerator()
        val tokens = tokenGenerator.generateTokens(testCode)

        Assert.assertEquals(6, tokens.size )

        assert(tokens[0] is Token.VariableToken)
        assert(tokens[1] is Token.IntTypeToken)
        assert(tokens[2] is Token.NameToken)
        assert((tokens[2] as Token.NameToken).name == "x")
        assert(tokens[3] is Token.AssignValueToken)
        assert(tokens[4] is Token.LiteralIntToken)
        assert(tokens[5] is Token.CloseSentenceToken)
    }

    @Test
    fun `test generate tokens for two assign variable int`() {
        val testCode = "variable integer x=10; " +
                "variable integer y = 11;"
        val tokenGenerator = TokenGenerator()
        val tokens = tokenGenerator.generateTokens(testCode)

        Assert.assertEquals(12, tokens.size )

        assert(tokens[0] is Token.VariableToken)
        assert(tokens[1] is Token.IntTypeToken)
        assert(tokens[2] is Token.NameToken)
        assert(tokens[3] is Token.AssignValueToken)
        assert(tokens[4] is Token.LiteralIntToken)
        assert((tokens[4] as Token.LiteralIntToken).literalValue == 10)
        assert(tokens[5] is Token.CloseSentenceToken)

        assert(tokens[6] is Token.VariableToken)
        assert(tokens[7] is Token.IntTypeToken)
        assert(tokens[8] is Token.NameToken)
        assert(tokens[9] is Token.AssignValueToken)
        assert(tokens[10] is Token.LiteralIntToken)
        assert((tokens[10] as Token.LiteralIntToken).literalValue == 11)
        assert(tokens[11] is Token.CloseSentenceToken)
    }

}