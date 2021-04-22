package lvc.com.languageofleo

import org.junit.Assert
import org.junit.Test

class ReserveWordsTest {

    @Test
    fun testIfAsReservedWord() {
       Assert.assertTrue(ReservedWords.isReservedWord("if"))
    }

}