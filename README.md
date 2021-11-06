# reversi-desktop

Java implementation of the famous Reversi game with AI.

## Build

To build class symbols using the command line, assuming you're in the root reposority folder, use:

```
$ javac -d bin -cp libs/forms_rt.jar src/reversi/**/*.java
```

## Running

To run the CLI version use:

```
$ java -cp bin reversi.Program
```

To run the GUI version use:
```
java -cp libs/forms_rt.jar:bin reversi.gui.GameWindow
```

With the GUI version you can select to play against the IA, or against another player.  
AI players don't play automatically, you have to click on the board to trigger their next move
