# reversi-desktop

Java implementation of the famous Reversi game with AI.

## Build

To build class symbols using the command line, assuming you're in the root reposority folder, use:

```
$ javac -d bin -cp libs/forms_rt.jar src/reversi/**/*.java
```

Then to run the CLI version use:

```-Xlint:unchecked
$ java -cp bin reversi.Program
```
