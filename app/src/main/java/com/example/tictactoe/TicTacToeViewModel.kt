package com.example.tictactoe

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class TicTacToeViewModel : ViewModel() {
    private val _state = mutableStateOf(TicTacToeState())
    val state: State<TicTacToeState> = _state

    private val _history = mutableStateOf(listOf<TicTacToeHistoryItem>())
    val history: State<List<TicTacToeHistoryItem>> = _history

    fun setGridSize(size: Int) {
        _state.value = TicTacToeState(gridSize = size)
    }

    fun setButton(index: Int) {
        val currentState = state.value
        if (currentState.buttonValues[index] == "-" && currentState.victor == null) {
            val newButtonValues = currentState.buttonValues.copyOf()
            val newButtonWinners = currentState.buttonWinners.copyOf()
            newButtonValues[index] = if (currentState.isXTurn) "X" else "O"
            newButtonWinners[index] = currentState.isXTurn
            val newVictor = checkWinner(newButtonValues)

            _state.value = currentState.copy(
                buttonValues = newButtonValues,
                buttonWinners = newButtonWinners,
                isXTurn = !currentState.isXTurn,
                victor = newVictor
            )

            if (newVictor != null || !newButtonValues.contains("-")) {
                saveGameHistory(newVictor ?: "Draw")
            }
        }
    }

    private fun checkWinner(buttonValues: Array<String>): String? {
        val gridSize = state.value.gridSize
        val lines = mutableListOf<List<String>>()

        // Rows
        for (i in 0 until gridSize) {
            lines.add(buttonValues.slice(i * gridSize until (i + 1) * gridSize))
        }

        for (i in 0 until gridSize) {
            lines.add(buttonValues.filterIndexed { index, _ -> index % gridSize == i })
        }

        lines.add(buttonValues.filterIndexed { index, _ -> index % (gridSize + 1) == 0 })
        lines.add(buttonValues.filterIndexed { index, _ -> index % (gridSize - 1) == 0 && index != 0 && index != buttonValues.size - 1 })

        for (line in lines) {
            if (line.all { it == "X" }) return "X"
            if (line.all { it == "O" }) return "O"
        }

        return null
    }

    fun resetBoard() {
        _state.value = TicTacToeState()
    }

    fun showHistory() {
        _history.value = fetchGameHistory()
    }

    private fun fetchGameHistory(): List<TicTacToeHistoryItem> {
        return _history.value
    }

    private fun saveGameHistory(winner: String) {
        viewModelScope.launch {
            val historyItem = TicTacToeHistoryItem(
                state = _state.value,
                winner = winner
            )
            _history.value = _history.value + historyItem
        }
    }
}

data class TicTacToeHistoryItem(
    val state: TicTacToeState,
    val winner: String
)
