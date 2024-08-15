package com.example.tictactoe

data class TicTacToeState(
    val gridSize: Int = 3,
    val buttonValues: Array<String> = Array(gridSize * gridSize) { "-" },
    val buttonWinners: Array<Boolean> = Array(gridSize * gridSize) { false },
    val isXTurn: Boolean = true,
    val victor: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TicTacToeState

        if (!buttonValues.contentEquals(other.buttonValues)) return false
        if (!buttonWinners.contentEquals(other.buttonWinners)) return false
        if (isXTurn != other.isXTurn) return false
        if (victor != other.victor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buttonValues.contentHashCode()
        result = 31 * result + buttonWinners.contentHashCode()
        result = 31 * result + isXTurn.hashCode()
        result = 31 * result + (victor?.hashCode() ?: 0)
        return result
    }
}
