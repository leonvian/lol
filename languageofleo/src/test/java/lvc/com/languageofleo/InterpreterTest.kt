package lvc.com.languageofleo

import org.junit.Assert
import org.junit.Test
import kotlin.math.log

class InterpreterTest  {

    @Test
    fun testSumPlus1Execution() {
        val testCode =
                    "  variable integer x = 10;\n" +
                    "  variable integer y = 5;\n" +
                    "  variable integer z = x + y;\n" +
                    "   startProgram {\n" +
                    "     print(z + 1);\n" +
                    "  }"

        val interpreter = Interpreter()
        val logs = interpreter.execute(testCode)

        Assert.assertEquals(17, logs.size)
        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[0].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[1].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[2].executedInstruction)

        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[3].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[4].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[5].executedInstruction)

        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[6].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[7].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[8].executedInstruction)
        Assert.assertEquals(Sum::class.java.simpleName, logs[9].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[10].executedInstruction)

        Assert.assertEquals(StartProgram::class.java.simpleName, logs[11].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[12].executedInstruction)
        Assert.assertEquals(logs[12].memoryStateAfter!!.peek().varValue, 15)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[13].executedInstruction)
        Assert.assertEquals(Sum::class.java.simpleName, logs[14].executedInstruction)
        Assert.assertEquals(16, logs[15].memoryStateBefore.peek().varValue)
        Assert.assertEquals(Print::class.java.simpleName, logs[15].executedInstruction)
        Assert.assertEquals(EndProgram::class.java.simpleName, logs[16].executedInstruction)
    }

    @Test
    fun testSumExecution() {
        val testCode =
                    "  variable integer x = 10;\n" +
                    "  variable integer y = 5;\n" +
                    "  variable integer z = x + y;\n" +
                    "   startProgram {\n" +
                    "     print(z);\n" +
                    "  }"

        val interpreter = Interpreter()
        val logs = interpreter.execute(testCode)

        Assert.assertEquals(15, logs.size)
        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[0].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[1].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[2].executedInstruction)

        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[3].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[4].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[5].executedInstruction)

        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[6].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[7].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[8].executedInstruction)
        Assert.assertEquals(Sum::class.java.simpleName, logs[9].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[10].executedInstruction)

        Assert.assertEquals(StartProgram::class.java.simpleName, logs[11].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[12].executedInstruction)
        Assert.assertEquals(logs[12].memoryStateAfter!!.peek().varValue, 15)
        Assert.assertEquals(Print::class.java.simpleName, logs[13].executedInstruction)
        Assert.assertEquals(EndProgram::class.java.simpleName, logs[14].executedInstruction)
    }

    @Test
    fun testLongSumExecution() {
        val testCode =
                    "  variable integer x = 10;\n" +
                    "  variable integer y = 5;\n" +
                    "  variable integer z = x + y + 20 + 25;\n" +
                    "   startProgram {\n" +
                    "     print(z);\n" +
                    "  }"

        val interpreter = Interpreter()
        val logs = interpreter.execute(testCode)

        Assert.assertEquals(19, logs.size)
        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[0].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[1].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[2].executedInstruction)

        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[3].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[4].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[5].executedInstruction)

        Assert.assertEquals(DeclareVariable::class.java.simpleName, logs[6].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[7].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[8].executedInstruction)
        Assert.assertEquals(Sum::class.java.simpleName, logs[9].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[10].executedInstruction)
        Assert.assertEquals(Sum::class.java.simpleName, logs[11].executedInstruction)
        Assert.assertEquals(PushLiteralOnMemory::class.java.simpleName, logs[12].executedInstruction)
        Assert.assertEquals(Sum::class.java.simpleName, logs[13].executedInstruction)
        Assert.assertEquals(SetValueVariable::class.java.simpleName, logs[14].executedInstruction)

        Assert.assertEquals(StartProgram::class.java.simpleName, logs[15].executedInstruction)
        Assert.assertEquals(PushVarValueOnMemory::class.java.simpleName, logs[16].executedInstruction)
        Assert.assertEquals(60, logs[17].memoryStateBefore.peek().varValue)
        Assert.assertEquals(Print::class.java.simpleName, logs[17].executedInstruction)
        Assert.assertEquals(EndProgram::class.java.simpleName, logs[18].executedInstruction)
    }

    @Test
    fun testSimpleStringPrint() {
        val testCode =
                "startProgram {" +
                "print(\"Hello World\");" +
                "}"

        val interpreter = Interpreter()
        val logs = interpreter.execute(testCode)

        val log = logs.find {
            it.executedInstruction == Print::class.java.simpleName
        }
        Assert.assertEquals("Hello World", log!!.memoryStateBefore.peek().varValue.toString())
    }

}