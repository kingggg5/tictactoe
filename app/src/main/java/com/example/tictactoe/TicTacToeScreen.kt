package com.example.tictactoe

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    viewModel: TicTacToeViewModel = viewModel()
) {
    val state by viewModel.state
    val history by viewModel.history
    var gridSizeInput by remember { mutableStateOf(state.gridSize.toString()) }
    var selectedReplay by remember { mutableStateOf(-1) }
    var isHistoryVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val turn = if (state.isXTurn) "X Turn" else "O Turn"
        val turnMessage = "XO Game\nIt's $turn"
        val winner = state.victor
        val winnerMessage = "XO Game\n$winner Wins"
        Text(
            text = if (winner != null) winnerMessage else turnMessage,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(16.dp),
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = gridSizeInput,
            onValueChange = { gridSizeInput = it },
            label = { Text("Grid Size") },
            modifier = modifier.padding(16.dp)
        )

        Button(
            onClick = {
                val size = gridSizeInput.toIntOrNull() ?: 3
                viewModel.setGridSize(size)
                selectedReplay = -1
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(text = "Set Grid Size", fontSize = 32.sp)
        }

        Button(
            onClick = {
                viewModel.showHistory()
                isHistoryVisible = true // Show history after fetching
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(text = "View History", fontSize = 32.sp)
        }

        if (isHistoryVisible && history.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface)
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                LazyColumn {
                    items(history) { item ->
                        Button(
                            onClick = { selectedReplay = history.indexOf(item) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedReplay == history.indexOf(item)) Color.Gray else MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Replay ${history.indexOf(item) + 1}: ${item.winner} Wins")
                        }
                    }
                }
            }
        }

        if (selectedReplay >= 0 && selectedReplay < history.size) {
            val replay = history[selectedReplay]
            val replayState = replay.state
            for (rowId in 0 until replayState.gridSize) {
                BuildRow(rowId = rowId, state = replayState)
            }
        } else {
            for (rowId in 0 until state.gridSize) {
                BuildRow(rowId = rowId, viewModel = viewModel)
            }
        }

        Button(
            onClick = {
                viewModel.resetBoard()
                selectedReplay = -1
                isHistoryVisible = false
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(text = "Reset Game", fontSize = 32.sp)
        }
    }
}

@Composable
fun BuildRow(rowId: Int, state: TicTacToeState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (colId in 0 until state.gridSize) {
            val cellIndex = rowId * state.gridSize + colId
            Button(
                onClick = { /* Handle click here */ },
                modifier = Modifier.size(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (state.buttonValues[cellIndex]) {
                        "X" -> Color.Red
                        "O" -> Color.Black
                        else -> Color.Gray
                    },
                    contentColor = Color.White
                )
            ) {
                Text(text = state.buttonValues[cellIndex])
            }
        }
    }
}

@Composable
fun BuildRow(rowId: Int, viewModel: TicTacToeViewModel) {
    val state by viewModel.state
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (colId in 0 until state.gridSize) {
            val cellIndex = rowId * state.gridSize + colId
            Button(
                onClick = { viewModel.setButton(cellIndex) },
                modifier = Modifier.size(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (state.buttonValues[cellIndex]) {
                        "X" -> Color.Red
                        "O" -> Color.Black
                        else -> Color.Gray
                    },
                    contentColor = Color.White
                )
            ) {
                Text(text = state.buttonValues[cellIndex])
            }
        }
    }
}
