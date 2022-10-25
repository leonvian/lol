package lvc.com.languageofleo

/**
 * Prepare the code to be easier to transform into list of Tokens.
 *
 */
object CodePreProcess {

    fun process(codeInput: String): String {
        var code = codeInput.trim()
        ReservedWords.getNotSpacedReservedWords().forEach {
          code = code.replace(it.word, " ${it.word} ")
        }
        return code
    }

}