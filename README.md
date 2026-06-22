# Minesweeper

Command line minesweeper built in Java. 9x9 grid with 10 mines.

## How to Run

```
javac Minesweeper.java
java Minesweeper
```

## How to Play

Each turn you pick an action and then enter the row and column coordinates (0-8).

- **R** – reveal a cell
- **F** – flag/unflag a cell you think has a mine

**You win** by correctly flagging all 10 mines. **You lose** if you reveal a mine.

## Notes

- Coordinates are row first, then column
- You can toggle flags on and off
- All mines are revealed on a loss
