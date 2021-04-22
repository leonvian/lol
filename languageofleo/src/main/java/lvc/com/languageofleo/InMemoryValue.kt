package lvc.com.languageofleo

data class InMemoryValue(val varValue: Any, val type: Type) {

    fun isNumeric(): Boolean = type == Type.INT || type == Type.DOUBLE

}