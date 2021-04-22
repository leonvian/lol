package lvc.com.languageofleo

import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class InstructionGenerator {

    private var current = 0

    fun generateInstructions(tokens: List<Token>): List<Instruction> {
        val instructions = arrayListOf<Instruction>()

        while (tokens.hasTokens()) {
            val currentToken = tokens.pullFirst()
            val newInstruction = createInstruction(currentToken, tokens)
            instructions.addAll(newInstruction)
        }

        return instructions
    }

    private fun createInstruction(token: Token, tokens: List<Token>): List<Instruction> {
        val instructions = arrayListOf<Instruction>()
        when (token) {
            is Token.VariableToken -> {
                instructions.add(createVariableDeclarationInstruction(tokens))
            }
            is Token.AssignValueToken -> {
                instructions.addAll(createAssignValueInstruction(tokens))
            }
            is Token.OpenBlockToken -> {

            }
            is Token.CloseSentenceToken -> {
                // instructions.add(CloseSentence)
            }
            is Token.StartProgram -> {
                instructions.add(StartProgram)
            }
            is Token.Print -> {
                instructions.addAll(createPrintInstruction(tokens))
            }
            is Token.CloseBlockToken -> {
                instructions.add(EndProgram)
            }

            else -> {
                throw InvalidTokenUsageException("Unrecognized token $token")
            }
        }

        return instructions
    }

    private fun createPrintInstruction(tokens: List<Token>): List<Instruction> {
        val generatedInstructions = arrayListOf<Instruction>()

        throwSyntaxExceptionIfNot(
            tokens.pullFirst().isOpenParenthesis(),
            "Missing open parenthesis -> ("
        )

        if (tokens.peekFirst() == Token.StringBracket) {
            generatedInstructions.add(createPushLiteralStringInstruction(tokens))
        } else {
            generatedInstructions.addAll(generateMathAssignInstructions(tokens, Token.CloseParenthesisToken))
        }

        throwSyntaxExceptionIfNot(
            tokens.pullFirst().isCloseParenthesis(),
            "Missing close parenthesis -> )"
        )
        throwSyntaxExceptionIfNot(
            tokens.pullFirst().isCloseSentence(),
            "Missing close sentence -> ; "
        )

        generatedInstructions.add(Print)
        return generatedInstructions
    }

    private fun createPushLiteralStringInstruction(tokens: List<Token>): PushLiteralOnMemory {
        tokens.pullFirst()
        val stringBuilder = StringBuilder()
        while (tokens.peekFirst() != Token.StringBracket) {
            stringBuilder.append(tokens.pullFirst().getLiteralValue())
            if (tokens.peekFirst() != Token.StringBracket) {
                stringBuilder.append(" ")
            }
        }
        tokens.pullFirst()

       return  PushLiteralOnMemory(stringBuilder.toString(), Type.STRING)
    }

    private fun createVariableDeclarationInstruction(tokens: List<Token>): Instruction {
        val type = tokens.pullFirst().getType()
        val varName = tokens.pullFirst().getNameAttr()

        return DeclareVariable(varName, type)
    }

    private fun createAssignValueInstruction(tokens: List<Token>): List<Instruction> {
        val varName = tokens.peekPreviousToken(2).getNameAttr()
        val generatedInstructions = generateMathAssignInstructions(tokens, Token.CloseSentenceToken)
        generatedInstructions.add(SetValueVariable(varName))

        return generatedInstructions
    }

    private fun generateMathAssignInstructions(tokens: List<Token>, breakToken: Token): ArrayList<Instruction> {
        val literalsOrVariables = LinkedList<Instruction>()
        val operators = LinkedList<Instruction>()

        while (tokens.peekFirst() != breakToken) {
            val currentToken = tokens.pullFirst()
            when {
                currentToken.isLiteralValue() -> {
                    literalsOrVariables.add(createPushLiteralInstruction(currentToken))
                }
                currentToken is Token.NameToken -> {
                    literalsOrVariables.add(PushVarValueOnMemory(currentToken.name))
                }
                currentToken.isMathOperator() -> {
                    operators.push(createInstructionByMathOperator(currentToken))
                }
                else -> throw InvalidTokenUsageException("Unexpected token $currentToken")
            }
        }

        val generatedInstructions = arrayListOf<Instruction>()
        generatedInstructions.add(literalsOrVariables.poll())
        while (operators.isNotEmpty()) {
            generatedInstructions.add(literalsOrVariables.poll())
            generatedInstructions.add(operators.poll())
        }
        return generatedInstructions
    }

    private fun createPushLiteralInstruction(token: Token): PushLiteralOnMemory {
        val varValue = token.getLiteralValue()
        val type = token.getLiteralType()
        return PushLiteralOnMemory(varValue, type)
    }

    private fun createInstructionByMathOperator(token: Token): Instruction {
        return when (token) {
            is Token.OperatorSumToken -> Sum
            is Token.OperatorSubtractToken -> Subtract
            is Token.OperatorMutiplyToken -> Multiply
            is Token.OperatorDivisionToken -> Division
            else -> throw InvalidTokenUsageException("Unexpected token $token Math operator expecter here")
        }
    }

    private fun List<Token>.hasTokens() = current < size

    private fun List<Token>.pullFirst(): Token {
        val token = get(current)
        current++

        return token
    }

    private fun List<Token>.peekFirst(): Token = get(current)

    private fun List<Token>.peekPreviousToken(previous: Int): Token = get(current - previous)

    // TODO Make this print tokens (before and after error execution), so the user will know where the bug happens
    private fun throwSyntaxExceptionIfNot(validation: Boolean, errorMessage: String? = "") {
        if (!validation)
            throw SyntaxException(errorMessage.orEmpty())
    }

}