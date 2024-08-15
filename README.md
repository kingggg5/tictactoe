# XO Game Source Code

## Overview

The XO Game (also known as Tic-Tac-Toe) is a classic 3x3 grid game where two players (X and O) take turns marking cells in a grid.

## Features

- **Customizable Grid Size**: Play with different grid sizes
- **Game History**: Save game results (winner or draw) along with moves and replay past games.
- **Replay Functionality**: Replay any saved game from the history.
- **Reset Functionality**: Reset game to the start
- **Jetpack Compose Design**: for UI construction

## Installation

### Prerequisites

- Android Studio
- Kotlin 
- Jetpack Compose

### Steps to Get Started

1. **Clone the Repository**

   Clone the repository to your local machine using Git:

   ```bash
    git clone https://github.com/your-username/tictactoe.git
   cd tictactoe

2. **Open the project**

  Launch Android Studio
  Select Open an existing project and navigate to the cloned repository directory
  
3. **Build and Sync**
  Android Studio should automatically detect the Gradle build files. Click on Sync Now if prompted.
  Make sure all dependencies are resolved and the project builds successfully

4. **Run the Application**
  Connect an Android device or start an emulator
  Click on the Run button in Android Studio 

5. **Interact with the game**
  You can set the grid size, make moves, and view game history.
## Usage

1. **Set the Grid Size**

    - By default, the game starts with a 3x3 grid. Adjust the grid size by entering a number greater than or equal to 3 in the input field and clicking "Play."

2. **Play the Game**

    - Click on the cells in the grid to place your marker ('X' or 'O').
    - The game will automatically determine if there is a win or a draw and will save the game state.

3. **View and Replay Game History and Reset Game**

    - Click on "Show History" to display a list of past games.
    - Replay a game by clicking the "Replay" button next to the game entry.
    - Reset a game by clicking the "Reset Game" button next to the Replay.

## Algorithm

1. **Grid Representation**
    - The board is represented as a list of strings, with "X", "O", or empty strings for each cell.

2. **Turn Management**
    - A boolean flag tracks which player's turn it is

3. **Win Check**
    - The game checks rows, columns, and diagonals for a winning condition

4. **Saving and Displaying Game History**
    - Game states are saved to allow replaying of previous games

5. **Replay Game**
    - The replayGame function allows users to replay a saved game by resetting the grid and replaying the moves from the saved history.