package lvc.com.languageofleo

import java.util.*

data class InstructionLog(val executedInstruction: String,
                          val memoryStateBefore: Stack<InMemoryValue>,
                          var memoryStateAfter: Stack<InMemoryValue>? = null)

