The main application file is in App.java

To run the application:

```
./gradlew clean build run
```

To run the test:

```
./gradlew clean build test
```

## Some configurations in the application include
1. Batch size is 100, which means, The matcher task will perform the computation for 100 lines of text in a single thread 
2. Hence, the charOffset will have the value of the index of the word found within a given batch. For example, if the word "Arthur" is found in line number 101 and at the 5 index from the line, the charOffset will be 5.

## Cause for possible mismatch of the charOffset when compared with what the text editors say the char offset is
When we observe the charOffset of a particular word from the beginning of the batch by selecting the specified text in a text editor like VSCode, it may not be equal to what the program gives as the output. Its because, text editors consider the newline char as an additional character, but the program does not. If it is intended to be considered as another character, then we need to tweak the program again. 