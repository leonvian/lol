package lvc.com.languageofleo

import java.util.*
import kotlin.collections.HashMap

class Interpreter {

    private val memory: Stack<InMemoryValue> = Stack()
    private val variables: HashMap<String, InMemoryValue> = HashMap()
    private val callStack: Stack<Int> = Stack()
    private var executionPointer: Int = 0

    fun execute(code: String): List<InstructionLog> {
        val tokens = TokenGenerator().generateTokens(code)
        val instructions = InstructionGenerator().generateInstructions(tokens)

        val logs = execute(instructions)
        return logs
    }

    private fun execute(instructions: List<Instruction>): List<InstructionLog> {
        val logs = arrayListOf<InstructionLog>()
        while (true) { // Executions will end at EndProgram execution
            val currentInstruction = instructions[executionPointer]

            // EndMethod is a different instruction since it change the execution point
            if (currentInstruction is EndMethod) {
                executeEndMethod(currentInstruction)
                continue
            }

            logs.addLog(currentInstruction)

            when (currentInstruction) {
                is StartProgram -> {
                    // EMPTY
                }
                is DeclareVariable -> {
                    executeDeclareVariable(currentInstruction)
                }
                is SetValueVariable -> {
                    executeSetValueVariable(currentInstruction)
                }
                is PushVarValueOnMemory -> {
                    executeGetValueVariable(currentInstruction)
                }
                is PushLiteralOnMemory -> {
                    executePushLiteral(currentInstruction)
                }
                is StartMethod -> {
                    executeStartMethod(currentInstruction)
                }
                is Sum -> {
                    executeSum()
                }
                is Print -> {
                    print()
                }
                is EndProgram -> {
                    return logs
                }
            }

            logs.finishLastLog()
            executionPointer ++
        }
    }

    private fun ArrayList<InstructionLog>.addLog(instruction: Instruction) {
        add(InstructionLog(executedInstruction = instruction.javaClass.simpleName, memoryStateBefore =  memory.clone() as Stack<InMemoryValue>))
    }

    private fun ArrayList<InstructionLog>.finishLastLog() {
        last().memoryStateAfter = memory.clone() as Stack<InMemoryValue>
    }

    private fun executeStartMethod(startMethod: StartMethod) {
        callStack.push(executionPointer)
        executionPointer = startMethod.startPoint
    }

    private fun executeEndMethod(endMethod: EndMethod) {
        executionPointer = callStack.pop()
    }

    private fun executeDeclareVariable(declareVariable: DeclareVariable) {
        when(declareVariable.type) {
          Type.INT -> variables[declareVariable.varName] = InMemoryValue(0, Type.INT)

          Type.DOUBLE -> variables[declareVariable.varName] = InMemoryValue(0.0, Type.DOUBLE)

          Type.STRING -> variables[declareVariable.varName] = InMemoryValue("", Type.STRING)
        }
    }

    private fun executeSetValueVariable(setValueVariable: SetValueVariable) {
        val varName = setValueVariable.varName

        val value = setValueVariable.varValue?.let {
             InMemoryValue(it, typeByVarName(varName))
        }?:  InMemoryValue(memory.pop().varValue, typeByVarName(varName))

        variables[varName] = value
    }

    private fun executeGetValueVariable(pushVarValueOnMemory: PushVarValueOnMemory) {
        val varName = pushVarValueOnMemory.varName
        memory.add(variables[varName])
    }

    private fun executePushLiteral(pushLiteralOnMemory: PushLiteralOnMemory) {
        val varValue = pushLiteralOnMemory.varValue
        val type = pushLiteralOnMemory.type
        memory.add(InMemoryValue(varValue, type))
    }

    private fun executeSum() {
        val inMemory1 = memory.pop()
        val inMemory2 = memory.pop()

        if (inMemory1.isNumeric() && inMemory2.isNumeric()) {
           val value =  inMemory1.varValue.toString().toInt() + inMemory2.varValue.toString().toInt()
           memory.add(InMemoryValue(value, Type.INT))
        }
    }

    private fun print() {
        System.out.println("${memory.pop().varValue}")
    }

    private fun typeByVarName(varName: String): Type =
        variables[varName]!!.type

}