package com.jarq.app.ciphers;

import com.jarq.app.exceptions.InvalidKey;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Playfair extends AbstractCipher {

    private final int ROW_INDEX = 0;
    private final int COLUMN_INDEX = 1;
    private final int NUMBER_OF_ROWS = 5;
    private final int NUMBER_OF_COLUMNS = 5;
    private final int BORDER_INDEX = 4;
    private final int ASCI_J_Number = 106;
    private final char CHAR_TO_BE_REPLACED = 'j';
    private final char CHAR_TO_REPLACE_I = 'i';
    private final char CHAR_USED_TO_ADD = 'x';
    private final int ASCI_A_NUM = 97;
    private final int ASCI_Z_NUM = 122;
    private String key;
    private char[] charList;
    private LinkedList<char[]> keyMatrix;
    private ArrayList<int[]> indexesPairs;

    public Playfair() {
        this.name = "Playfair";
        this.description = "first cipher to encrypt pairs of letters in cryptologic history.\n"+
                "        The first recorded description of the Playfair cipher was in a document\n"+
                "        signed by Wheatstone on 26 March 1854";
        this.isKeyRequired = true;
        this.key = "monarchy";
        this.charList = new char[1];
        this.keyMatrix = new LinkedList<>();
        this.indexesPairs = new ArrayList<int[]>();
    }

    @Override
    public String getKeyInfo() {
        return "key should be word";
    }

    public void setKey(String newKey) {
        this.key = key;
    }

    @Override
    public void changeKey(String newKey) throws InvalidKey {
        Pattern regex = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = regex.matcher(newKey);
        if (matcher.find()){
            this.setKey(newKey);
        } else {
            throw new InvalidKey();
        }
    }

    protected String encrypt(String text) {
        updateTables(text);
        encryptIndexesPairs();
        updateCharList();
        return getOutputText();
    }

    protected  String decrypt(String text) {
        updateTables(text);
        decryptIndexesPairs();
        updateCharList();
        return getOutputText();
    }

    private void setCharList(char[] newTable) {
        charList = newTable;
    }

    private void setKeyMatrix(LinkedList<char[]> newKeyMatrix) {
        keyMatrix = newKeyMatrix;
    }

    private void setIndexesPairs(ArrayList<int[]> newIndexesPairs) {
        indexesPairs = newIndexesPairs;
    }

    private void updateTables(String text) {
        setCharListFromGivenText(text);
        setKeyMatrix();
        fillIndexesPairs();
        updateCharList();
    }

    private String getOutputText() {
        int wordLength = 4;
        String wordDivider = " ";
        int currentIndex = 0;
        String outputText = "";
        for (char character : charList) {
            outputText += String.valueOf(character);
            currentIndex++;
            if (currentIndex == wordLength) {
                outputText += wordDivider;
                currentIndex = 0;
            }
        }
        return outputText;
    }

    private void encryptIndexesPairs() {
        int[] previousPair, currentPair;
        int previous, current;
        for (int i = 0; i < indexesPairs.size(); i++) {
            if (i % 2 != 0) {
                previous = i - 1;
                current = i;
                previousPair = indexesPairs.get(previous);
                currentPair = indexesPairs.get(current);
                if (previousPair[ROW_INDEX] == currentPair[ROW_INDEX]) {
                    indexesPairs.set(previous, encryptColumn(previousPair));
                    indexesPairs.set(current, encryptColumn(currentPair));
                } else if ((previousPair[COLUMN_INDEX] == currentPair[COLUMN_INDEX])) {
                    indexesPairs.set(previous, encryptRow(previousPair));
                    indexesPairs.set(current, encryptRow(currentPair));
                } else {
                    int[][] tmp = getSwapIndexes(previousPair, currentPair);
                    indexesPairs.set(previous, tmp[0]);
                    indexesPairs.set(current, tmp[1]);
                }
            }
        }
    }

    private void decryptIndexesPairs() {
        int[] previousPair, currentPair;
        int previous, current;
        for (int i = 0; i < indexesPairs.size(); i++) {
            if (i % 2 != 0) {
                previous = i - 1;
                current = i;
                previousPair = indexesPairs.get(previous);
                currentPair = indexesPairs.get(current);
                if (previousPair[ROW_INDEX] == currentPair[ROW_INDEX]) {
                    indexesPairs.set(previous, decryptColumn(previousPair));
                    indexesPairs.set(current, decryptColumn(currentPair));
                } else if (previousPair[COLUMN_INDEX] == currentPair[COLUMN_INDEX]) {
                    indexesPairs.set(previous, decryptRow(previousPair));
                    indexesPairs.set(current, decryptRow(currentPair));
                } else {
                    int[][] tmp = getSwapIndexes(previousPair, currentPair);
                    indexesPairs.set(previous, tmp[0]);
                    indexesPairs.set(current, tmp[1]);
                }
            }
        }
    }

    private int[] encryptRow(int[] pair) {
        int[] tmp = new int[2];
        if (pair[ROW_INDEX] == BORDER_INDEX) {
            tmp[ROW_INDEX] = 0;
            tmp[COLUMN_INDEX] = pair[COLUMN_INDEX];
        } else {
            tmp[ROW_INDEX] = pair[ROW_INDEX] + 1;
            tmp[COLUMN_INDEX] = pair[COLUMN_INDEX];
        }
        return tmp;
    }

    private int[] encryptColumn(int[] pair) {
        int[] tmp = new int[2];
        if (pair[COLUMN_INDEX] == BORDER_INDEX) {
            tmp[COLUMN_INDEX] = 0;
            tmp[ROW_INDEX] = pair[ROW_INDEX];
        } else {
            tmp[COLUMN_INDEX] = pair[COLUMN_INDEX] + 1;
            tmp[ROW_INDEX] = pair[ROW_INDEX];
        }
        return tmp;
    }

    private int[][] getSwapIndexes(int[] firstPair, int[] secondPair) {
        int[] first;
        int[] second;
        if (firstPair[COLUMN_INDEX] < secondPair[COLUMN_INDEX]) {
            first = firstPair;
            second = secondPair;
        } else {
            first = secondPair;
            second = firstPair;
        }
        int colInd = firstPair[COLUMN_INDEX];
        firstPair[COLUMN_INDEX] = secondPair[COLUMN_INDEX];
        secondPair[COLUMN_INDEX] = colInd;
        int[][] tmp = new int[2][2];
        tmp[0] = firstPair;
        tmp[1] = secondPair;
        return tmp;
    }

    private int[] decryptRow(int[] pair) {
        int[] tmp = new int[2];
        if (pair[ROW_INDEX] == 0) {
            tmp[ROW_INDEX] = BORDER_INDEX;
            tmp[COLUMN_INDEX] = pair[COLUMN_INDEX];
        } else {
            tmp[ROW_INDEX] = pair[ROW_INDEX] - 1;
            tmp[COLUMN_INDEX] = pair[COLUMN_INDEX];
        }
        return tmp;
    }

    private int[] decryptColumn(int[] pair) {
        int[] tmp = new int[2];
        if (pair[COLUMN_INDEX] == 0) {
            tmp[COLUMN_INDEX] = BORDER_INDEX;
            tmp[ROW_INDEX] = pair[ROW_INDEX];
        } else {
            tmp[COLUMN_INDEX] = pair[COLUMN_INDEX] - 1;
            tmp[ROW_INDEX] = pair[ROW_INDEX];
        }
        return tmp;
    }

    private void updateCharList() {
        char[] tmpTable = new char[indexesPairs.size()];
        int currentIndex = 0;
        for (int[] pair : indexesPairs) {
            tmpTable[currentIndex] = getCharByIndexPair(pair);
            currentIndex++;
        }
        setCharList(tmpTable);
    }

    private void fillIndexesPairs() {
        ArrayList<int[]> indexesList = new ArrayList<>();
        for (char character : charList) {
            indexesList.add(getIndexesPair(character, keyMatrix));
        }
        setIndexesPairs(indexesList);
    }

    private int[] getIndexesPair(char character, LinkedList<char[]> keyMatrix) {
        int[] singleLetterIndexes = new int[2];
        Boolean indexesFounded = false;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            if (indexesFounded) break;
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                char letter = keyMatrix.get(i)[j];
                if (character == letter) {
                    indexesFounded = true;
                    singleLetterIndexes[0] = i;
                    singleLetterIndexes[1] = j;
                    break;
                }
            }
        }
        return singleLetterIndexes;
    }

    private char getCharByIndexPair(int[] indexPair) {
        return keyMatrix.get(indexPair[0])[indexPair[1]];
    }

    private String getStrippedStringFromText(String text) {
        LinkedList<String> tmpArray = new LinkedList<>(Arrays.asList(text.split(" ")));
        LinkedList<String> array = new LinkedList<>();
        for (String element : tmpArray) {
            if (element.trim().length() > 0) array.add((String) element);
        }
        return String.join(" ", array);
    }

    private LinkedList<char[]> getNestedListsOfCharsFromText(String text) {
        LinkedList<String> tmpArray = new LinkedList<>(Arrays.asList(text.split(" ")));
        LinkedList<char[]> outputArray = new LinkedList<>();
        char[] nestedArray;
        for (String element : tmpArray) {
            int wordLength = element.length();
            nestedArray = new char[wordLength];
            for (int i = 0; i < wordLength; i++) {
                nestedArray[i] = element.charAt(i);
            }
            outputArray.add(nestedArray);
        }
        return outputArray;
    }

    private void setCharListFromGivenText(String givenText) {
        String transformedText = getStrippedStringFromText(givenText);
        LinkedList<char[]> tableFromText = getNestedListsOfCharsFromText(transformedText);
        LinkedList<char[]> tmpArray = new LinkedList<>();
        char currentChar;
        Boolean isEven;
        for (char[] element : tableFromText) {
            int listLength = element.length;
            isEven = listLength % 2 == 0;
            for (int i = 0; i < listLength; i++) {
                currentChar = element[i];
                if (i > 0) {
                    if (element[i - 1] == currentChar & tmpArray.get(i - 1)[0] != CHAR_USED_TO_ADD)
                        currentChar = CHAR_USED_TO_ADD;
                }
                if (currentChar == CHAR_TO_BE_REPLACED) currentChar = CHAR_TO_REPLACE_I;
                char[] tmp = {Character.toLowerCase(currentChar)};
                tmpArray.add(tmp);
            }
            if (!isEven) {
                char[] tmp = {Character.toLowerCase(CHAR_USED_TO_ADD)};
                tmpArray.add(tmp);
            }
        }
        char[] outputTable = new char[tmpArray.size()];
        int currentIndex = 0;
        for (char[] table : tmpArray) {
            outputTable[currentIndex] = table[0];
            currentIndex++;
        }
        setCharList(outputTable);
    }

    private void setKeyMatrix() {
        String key = transformStringToLowerCaseLetters(this.key);
        char[] alphabetList;
        char[] nestedList;
        if (!checkIfKeyIsCorrect(key)) throw new java.lang.Error("Invalid key! Key should have only unique letters!");
        alphabetList = generateAlphabetList(ASCI_A_NUM, ASCI_Z_NUM);
        LinkedList<char[]> keyMatrix = createKeyMatrixList(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        keyMatrix = fillKeyMatrixUsingKey(keyMatrix, key);
        keyMatrix = fillKeyMatrixUsingAlphabetList(keyMatrix, alphabetList);
        setKeyMatrix(keyMatrix);
    }

    private char[] generateAlphabetList(int startAsciNum, int endAsciNum) {
        char[] alphabetList;
        int index = 0;
        alphabetList = new char[(endAsciNum - startAsciNum) + 1];
        for (int i = startAsciNum; i <= endAsciNum; i++) {
            if (i != ASCI_J_Number) {
                alphabetList[index] = (char) i;
                index++;
            }
        }
        return alphabetList;
    }

    private Boolean checkIfKeyIsCorrect(String givenKey) {
        Boolean isCorrect;
        LinkedList<String> tmpArray = new LinkedList<>(Arrays.asList(givenKey.split("")));
        Set<String> tmpSet = new HashSet<String>(tmpArray);
        int charAsciNum;
        isCorrect = tmpSet.size() == tmpArray.size();
        if (!isCorrect) return false;
        for (int i = 0; i < givenKey.length(); i++) {
            charAsciNum = (int) givenKey.charAt(i);
            if (charAsciNum < ASCI_A_NUM || ASCI_Z_NUM < charAsciNum) return false;
        }
        return isCorrect;
    }

    private String transformStringToLowerCaseLetters(String givenString) {
        String outputString = "";
        for (int i = 0; i < givenString.length(); i++)
            outputString += String.valueOf(Character.toLowerCase(givenString.charAt(i)));
        return outputString;
    }

    private LinkedList<char[]> createKeyMatrixList(int NUMBER_OF_ROWS, int NUMBER_OF_COLUMNS) {
        LinkedList<char[]> keyMatrixList = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            char[] nestedList = new char[NUMBER_OF_COLUMNS];
            keyMatrixList.add(nestedList);
        }
        return keyMatrixList;
    }

    private LinkedList<char[]> fillKeyMatrixUsingKey(LinkedList<char[]> keyMatrix, String key) {

        int lengthOfKey = key.length();
        Boolean isCompleted = false;
        int numberOfAddedLetters = 0;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (numberOfAddedLetters < lengthOfKey) keyMatrix.get(i)[j] = key.charAt(numberOfAddedLetters);
                else isCompleted = true;
                if (isCompleted) break;
                numberOfAddedLetters++;
            }
            if (isCompleted) break;
        }
        return keyMatrix;
    }

    private LinkedList<char[]> fillKeyMatrixUsingAlphabetList(LinkedList<char[]> keyMatrix, char[] alphabetList) {
        int alphabetListCurrentIndex = 0;
        Boolean isLetterAdded;

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            if (alphabetListCurrentIndex == alphabetList.length) break;
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                char letter = keyMatrix.get(i)[j];
                if (letter == 0) {
                    isLetterAdded = false;
                    while (!isLetterAdded) {
                        if (!checkIfLetterInkeyMatrix(keyMatrix, alphabetList[alphabetListCurrentIndex])) {
                            keyMatrix.get(i)[j] = alphabetList[alphabetListCurrentIndex];
                            isLetterAdded = true;
                        }
                        alphabetListCurrentIndex++;
                    }
                }
            }
        }
        return keyMatrix;
    }

    private Boolean checkIfLetterInkeyMatrix(LinkedList<char[]> keyMatrix, char letter) {
        int NUMBER_OF_ROWS = 5;
        int NUMBER_OF_COLUMNS = 5;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (keyMatrix.get(i)[j] == letter) return true;
                else if (keyMatrix.get(i)[j] == 0) return false;
            }
        }
        return false;
    }

    private void displayKeyMatrix() {
        for (char[] table : keyMatrix) {
            System.out.println(Arrays.toString(table));
        }
    }
}
