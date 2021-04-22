package lvc.com.languageofleo

sealed class Instruction

class DeclareVariable(val varName: String, val type: Type) : Instruction()

// In case of null,  value will be get from the top of memory
class SetValueVariable(val varName: String, val varValue: Any? = null) : Instruction()

class PushVarValueOnMemory(val varName: String) : Instruction()

class PushLiteralOnMemory(val varValue: Any, val type: Type) : Instruction()

object StartProgram : Instruction()

object EndProgram : Instruction()

// TODO add return type and arguments
class DeclareMethod(val methodName: String, val startPoint: Int) : Instruction()

class StartMethod(val methodName: String, val startPoint: Int) : Instruction()

class EndMethod(val methodName: String) : Instruction()

object Sum : Instruction()

object Subtract : Instruction()

object Multiply : Instruction()

object Division : Instruction()

object CloseSentence : Instruction()

object Print : Instruction()
