package lvc.com.languageofleo

import org.junit.Assert
import org.junit.Test

class InstructionGeneratorTest {

    @Test
    fun `test print with parameters`() {
        val testCode  = "print(10 + 5);"
        val tokens = TokenGenerator2().generateTokens(testCode)
        val instructions = InstructionGenerator().generateInstructions(tokens)

        Assert.assertEquals(4, instructions.size)
        assert(instructions[0] is PushLiteralOnMemory)
        assert(instructions[1] is PushLiteralOnMemory)
        assert(instructions[2] is Sum)
        assert(instructions[3] is Print)
    }

    @Test
    fun `test long sum variables and literals`() {
        val testCode  = "variable integer z = x + y + 20 + 25;"
        val tokens = TokenGenerator2().generateTokens(testCode)
        val instructions = InstructionGenerator().generateInstructions(tokens)

        Assert.assertEquals(9, instructions.size)
        assert(instructions[0] is DeclareVariable)
        assert(instructions[1] is PushVarValueOnMemory)
        assert(instructions[2] is PushVarValueOnMemory)
        assert(instructions[3] is Sum)
        assert(instructions[4] is PushLiteralOnMemory)
        assert(instructions[5] is Sum)
        assert(instructions[6] is PushLiteralOnMemory)
        assert(instructions[7] is Sum)
        assert(instructions[8] is SetValueVariable)
    }

    @Test
    fun `test generate instructions for sum code`() {
         val testCode =
                    "  variable integer x = 10;\n" +
                    "  variable integer y = 5;\n" +
                    "  variable integer z = x + y;\n" +
                    "   startProgram {\n" +
                    "     print(z);\n" +
                    "  }";

        val tokens = TokenGenerator2().generateTokens(testCode)
        val instructions = InstructionGenerator().generateInstructions(tokens)
        //Assert.assertEquals(3, instructions.size)

        assert(instructions[0] is DeclareVariable)
        val instructionDeclare = instructions[0] as DeclareVariable
        Assert.assertEquals(instructionDeclare.type, Type.INT)
        Assert.assertEquals(instructionDeclare.varName, "x")

        assert(instructions[1] is PushLiteralOnMemory)
        Assert.assertEquals((instructions[1] as PushLiteralOnMemory).varValue, 10)

        assert(instructions[2] is SetValueVariable)
        val instructionSerVarValue = instructions[2] as SetValueVariable
        Assert.assertEquals(instructionSerVarValue.varName, "x")

        assert(instructions[3] is DeclareVariable)
        val instructionDeclare1 = instructions[3] as DeclareVariable
        Assert.assertEquals(instructionDeclare1.type, Type.INT)
        Assert.assertEquals(instructionDeclare1.varName, "y")

        assert(instructions[4] is PushLiteralOnMemory)
        Assert.assertEquals((instructions[4] as PushLiteralOnMemory).varValue, 5)

        assert(instructions[5] is SetValueVariable)
        val instructionSerVarValue1 = instructions[5] as SetValueVariable
        Assert.assertEquals(instructionSerVarValue1.varName, "y")

        assert(instructions[6] is DeclareVariable)
        val instructionDeclare2 = instructions[6] as DeclareVariable
        Assert.assertEquals(instructionDeclare2.type, Type.INT)
        Assert.assertEquals(instructionDeclare2.varName, "z")

        assert(instructions[7] is PushVarValueOnMemory)
        Assert.assertEquals((instructions[7] as PushVarValueOnMemory).varName, "x")

        assert(instructions[8] is PushVarValueOnMemory)
        Assert.assertEquals((instructions[8] as PushVarValueOnMemory).varName, "y")

        assert(instructions[9] is Sum)

        assert(instructions[10] is SetValueVariable)
        Assert.assertEquals((instructions[10] as SetValueVariable).varName, "z")

        assert(instructions[11] is StartProgram)
        assert(instructions[12] is PushVarValueOnMemory)
        assert(instructions[13] is Print)
        assert(instructions[14] is EndProgram)
    }


    @Test
    fun `test generate instructions for assign variable int`() {
        val testCode = "variable integer x=10;"

        val tokens = TokenGenerator2().generateTokens(testCode)
        val instructions = InstructionGenerator().generateInstructions(tokens)
        Assert.assertEquals(3, instructions.size)

        assert(instructions[0] is DeclareVariable)
        val instructionDeclare = instructions[0] as DeclareVariable
        Assert.assertEquals(instructionDeclare.type, Type.INT)
        Assert.assertEquals(instructionDeclare.varName, "x")

        assert(instructions[1] is PushLiteralOnMemory)
        val pushLiteral = instructions[1] as PushLiteralOnMemory
        Assert.assertEquals(pushLiteral.varValue, 10)

        assert(instructions[2] is SetValueVariable)
        val instructionSerVarValue = instructions[2] as SetValueVariable
        Assert.assertEquals(instructionSerVarValue.varName, "x")
    }

}