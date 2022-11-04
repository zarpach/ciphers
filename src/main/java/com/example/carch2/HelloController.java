package com.example.carch2;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


public class HelloController {
    @FXML
    public Spinner<Integer> shift;
    @FXML
    public ToggleGroup group;
    @FXML
    public RadioButton toLeft, toRight;
    @FXML
    public Label initWordLength, keyWordLength, message, vegenreMessage, pairCipherLabel;
    @FXML
    private TextField encryptArea,
            decryptedTextArea,
            vegenreTextArea,
            vegenreKeyWord,
            vegenreEncrypted,
            pairCipherField,
            pairEncryptedArea,
            gammaWordField,
            gammaField,
            gammingResult,
            playfairWord,
            playfairKey,
            playfairResult;


    public final int x = 5;
    int[][] randomMatrix = new int[x][x];

    public void initialize() {
        initWordLength.textProperty().bind(vegenreTextArea.textProperty()
                .length()
                .asString("%d"));

        keyWordLength.textProperty().bind(vegenreKeyWord.textProperty()
                .length()
                .asString("%d"));
    }


    public void showMessage(int fromWhichCipher) {
        switch (fromWhichCipher) {
            case 1:
                message.setText("INVALID INPUT!");
            case 2:
                vegenreMessage.setText("Inputs are invalid! Try again without any digits.");
            case 3:
                pairCipherLabel.setText("Inputs are invalid! Try again without any digits.");
        }
    }

    @FXML
    protected void onEncryptButtonClick() {
        encryptArea.setText(encrypt(decryptedTextArea.getText(), shift.getValue(), true));
        decryptedTextArea.setText("");
    }

    @FXML
    protected void onDecryptButtonClick() {
        encryptArea.setText(decrypt(encryptArea.getText(), shift.getValue(), true));
    }

    @FXML
    public String encrypt(String str, int shift, boolean rightIsSelected) {
        StringBuilder strBuilder = new StringBuilder();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isLetter(c)) {
                if (rightIsSelected) {
                    c = (char) (str.charAt(i) + shift);
                } else {
                    c = (char) (str.charAt(i) - shift);
                }
                if ((Character.isLowerCase(str.charAt(i)) && c > 'z')
                        || (Character.isUpperCase(str.charAt(i)) && c > 'Z')) {
                    if (rightIsSelected) {
                        c = (char) (str.charAt(i) + shift);
                    } else {
                        c = (char) (str.charAt(i) - shift);
                    }
                }

                if (c + 0 > 122) {
                    c -= 26;
                } else if (c + 0 < 97) {
                    c += 26;
                }
            } else {
                showMessage(1);
            }
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }

    @FXML
    public String decrypt(String str, int shift, boolean rightIsSelected) {
        StringBuilder strBuilder = new StringBuilder();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isLetter(c)) {
                if (rightIsSelected) {
                    c = (char) (str.charAt(i) - shift);
                } else {
                    c = (char) (str.charAt(i) + shift);
                }

                if ((Character.isLowerCase(str.charAt(i)) && c > 'z')
                        || (Character.isUpperCase(str.charAt(i)) && c > 'Z')) {
                    if (rightIsSelected) {
                        c = (char) (str.charAt(i) - shift);
                    } else {
                        c = (char) (str.charAt(i) + shift);
                    }
                }

                if (c + 0 < 97) {
                    c += 26;
                } else if (c + 0 > 122) {
                    c -= 26;
                }
                System.out.println(str.charAt(i) - shift);
            }
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }

    @FXML
    public void vegenreEncryptMethod() {
        vegenreEncrypted.setText(vegenreEncrypt(vegenreTextArea.getText(), vegenreKeyWord.getText()));
    }

    @FXML
    public String vegenreEncrypt(String inputWord, String keyWord) {
        StringBuilder encryptedWord = new StringBuilder();
        int inputLength = inputWord.length();
        int keyLength = keyWord.length();

        int input = 0;
        char key = 0;

        for (int i = 0; i < inputWord.length(); i++) {
            try {
                input = inputWord.charAt(i);
                key = (char) (keyWord.charAt(i) - 'a');
                if (keyLength < inputLength) {
                    while (keyWord.length() <= inputLength) {
                        keyWord += keyWord;
                    }
                }
            } catch (StringIndexOutOfBoundsException r) {
                System.out.println("Error!");
            }
            if (Character.isLetter(inputWord.charAt(i)) && Character.isLetter(keyWord.charAt(i))) {
                input += key;
                input = (input % 122) + 'a';
                    if (input > 122) {
                        input -= 96;
                }
                encryptedWord.append((char) (input));
            } else {
                showMessage(2);
            }
        }
        return encryptedWord.toString();
    }
    @FXML
    public String vegenreDecrypt(String message, int shift) {
        return vegenreEncrypt(message, String.valueOf(26 - (shift % 26)));
    }

    @FXML
    public void pairEncryptMethod() {
        pairEncryptedArea.setText(pairEncrypt(pairCipherField.getText()));
        System.out.println("Check");
    }

    @FXML
    public String pairEncrypt(String word) {
        StringBuilder encryptedWord = new StringBuilder();
        int character = 0;

        for (int i = 0; i < word.length(); i++) {
            try {
                character = word.charAt(i);
            } catch (StringIndexOutOfBoundsException s) {
                System.out.println("Error from pair cipher");
            }

            if (Character.isLetter(character)) {
                if (character >= 97 && character < 110) {
                    character += 13;
                } else if (character >= 110 && character <= 122) {
                    character -= 13;
                }
                encryptedWord.append((char) (character));
            } else {
                showMessage(3);
            }
        }
        return encryptedWord.toString();
    }

    public void pairDecryptMethod() {
        pairEncryptedArea.setText(pairEncrypt(pairEncryptedArea.getText()));
    }

    public int toInt(ArrayList<Integer> array) {
        StringBuilder str = new StringBuilder();
        for (int i : array) {
            str.append(i);
        }
        return Integer.parseInt(String.valueOf(str));
    }

    public int toInt(int[] array) {
        StringBuilder str = new StringBuilder();
        for (int i : array) {
            str.append(i);
        }
        return Integer.parseInt(String.valueOf(str));
    }


    public int concatenate(long[] array) {
        String str = Arrays.stream(array)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());

        return Integer.parseInt(str);
    }

    public void invert(ArrayList<Integer> array) {
        for (int i = 0; i < array.size() / 2; i++) {
            int tmp = array.get(i);
            array.set(i, array.get(array.size() - i - 1));
            array.set(array.size() - i - 1, tmp);
        }
    }

    public int toBinary(int gamma) {
        int dividingResult = 1;
        ArrayList<Integer> binaryEl = new ArrayList<>();

        while (dividingResult != 0) {
            dividingResult = gamma / 2;
            binaryEl.add(gamma % 2);
            gamma = dividingResult;
        }

        invert(binaryEl);
        return toInt(binaryEl);
    }

    public int[] convertToArray(int number) {
        int i = 0;
        int length = (int) Math.log10(number);
        int divisor = (int) Math.pow(10, length);
        int[] temp = new int[length + 1];

        while (number != 0) {
            temp[i] = number / divisor;
            if (i < length) {
                ++i;
            }
            number = number % divisor;
            if (i != 0) {
                divisor = divisor / 10;
            }
        }
        return temp;
    }

    public int toDecimal(int binary) {
        int[] digits = convertToArray(binary);
        int result = 0;

        for (int i = 0, j = digits.length - 1; i != digits.length; i++, j--) {
            result += (digits[i]) * Math.pow(2, j);
        }
        return result;
    }


    public int keepInRange(int charIndex) {
        int shift;
        if (charIndex > 122) {
            shift = charIndex % 122;
            shift %= 26;
            charIndex = (shift + 97);

        } else if (charIndex < 97) {
            shift = 97 - charIndex;
            charIndex = 122 - shift;
        }
        return charIndex;
    }

    public int findBits(int x, int y) {
        int[] upper = convertToArray(x);
        int[] under = convertToArray(y);

        for (int i = upper.length - 1, j = under.length - 1; j >= 0; i--, j--) {
            upper[i] = upper[i] ^ under[j];
        }
        return toInt(upper);
    }

    public String gammaEncode(String unencoded, int num) {
        StringBuilder encoded = new StringBuilder();
        int[] array = convertToArray(num);
        int newChar;

        for (int i = 0; i < unencoded.length(); i++) {
            newChar = toDecimal(findBits(toBinary(unencoded.charAt(i)), toBinary(array[i])));
            encoded.append((char) (keepInRange(newChar)));
        }
        System.out.println("\n");
        return encoded.toString();
    }

    public long generateGamma(String word) {
        Random random = new Random();
        long[] newGamma = new long[word.length()];

        for (int i = 0; i < word.length(); i++) {
            newGamma[i] = random.nextInt(2,9);
        }

        return concatenate(newGamma);
    }

    public ArrayList<Integer> defineIndexes(String slice) {
        char index;
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int z = 0; z < slice.length(); z++) {
            index = slice.charAt(z);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < x; j++) {
                    if (randomMatrix[i][j] == index) {
                        indexes.add(i);
                        indexes.add(j);
                    }
                }
            }
        }
        return indexes;
    }

    public void printPrepared(int[][] matrix) {
        char c;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                c = (char) matrix[i][j];
                System.out.print(c + " ");
            }
            System.out.println("\t");
        }
    }

    public String makeUnique(String notUnique) {
        return notUnique.chars().
                distinct().
                collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public int[][] randomMatrix(String word) {
        randomMatrix = new int[x][x];
        boolean check = false;
        int index = 0;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x;) {
                if (index < word.length()) {
                    randomMatrix[i][j++] = word.charAt(index++);
                } else {
                    check = false;
                    int randomValue = (int) (Math.random() * (123 - 97) + 97);
                    for (int[] i1 : randomMatrix) {
                        for (int j1 : i1) {
                            if (j1 == randomValue || randomValue == 106) {
                                check = true;
                                break;
                            }
                        }
                    }
                    if (!check) {
                        randomMatrix[i][j] = randomValue;
                        j++;
                    }
                }
            }
        }
        return randomMatrix;
    }

    public ArrayList<String> chunk(String word) {
        ArrayList<String> strings = new ArrayList<>();
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (i == word.length() - 1) {
                newString.append(word.charAt(i));
            } else {
                newString.append(word.charAt(i));
                newString.append(word.charAt(++i));
            }
            strings.add(newString.toString());
            newString = new StringBuilder();
        }
        return strings;
    }

    public String shiftReplacement(String slice, boolean encode) {
        ArrayList<Integer> indexes = defineIndexes(slice);
        StringBuilder encoded = new StringBuilder();
        char c = 0;

        if (encode) {
            for (int i = 0, j = 1; i < x - 1; i+=2, j+=2) {
                if (Objects.equals(indexes.get(1), indexes.get(3))) {
                    c = (char) randomMatrix[(indexes.get(i) + 1) % x][indexes.get(j)];
                } else if (Objects.equals(indexes.get(0), indexes.get(2))) {
                    c = (char) randomMatrix[indexes.get(i)][(indexes.get(j) + 1) % x];
                }
                encoded.append(c);
            }
        } else {
            for (int i = 0, j = 1; i < x - 1; i+=2, j+=2) {
                if (Objects.equals(indexes.get(1), indexes.get(3))) {
                    c = (char) randomMatrix[((indexes.get(i) + 5) - 1) % x][indexes.get(j)];
                } else if (Objects.equals(indexes.get(0), indexes.get(2))) {
                    c = (char) randomMatrix[indexes.get(i)][((indexes.get(j) + 5) - 1) % x];
                }
                encoded.append(c);
            }
        }
        return encoded.toString();
    }

    public String squareReplacement(ArrayList<Integer> integers) {
        StringBuilder encoded = new StringBuilder(2);

        int temp = integers.get(0);
        int secondRow = integers.get(2);
        char c;

        integers.set(0, secondRow);
        integers.set(2, temp);

        for (int i = 2, j = 3; i > -1; i-=2, j-=2) {
            c = (char) randomMatrix[integers.get(i)][integers.get(j)];
            encoded.append(c);
        }

        System.out.println(integers);
        return encoded.toString();
    }

    public char oneCharReplacement(ArrayList<Integer> indexes, boolean encode) {
        if (encode) {
            return (char) randomMatrix[((indexes.get(0) + 5) - 1) % x][indexes.get(1)];
        } else {
            return (char) randomMatrix[(indexes.get(0) + 1) % x][indexes.get(1)];
        }
    }

    public String replace(String str) {
        StringBuilder newStr = new StringBuilder();
        char t;
        for (int i = 0; i < str.length(); i++) {
            t = str.charAt(i);
            if (t == 106) t -= 1;
            newStr.append(t);
        }
        return newStr.toString();
    }

    public String playfairEncode(String string, boolean encode) {
        StringBuilder encoded = new StringBuilder();
        ArrayList<Integer> indexes;
        ArrayList<String> strings = chunk(string);

        for (String s : strings) {
            s = replace(s);
            indexes = defineIndexes(s);

            if (s.length() == 1) {
                encoded.append(oneCharReplacement(indexes, encode));
            } else if (Objects.equals(indexes.get(0), indexes.get(2)) || Objects.equals(indexes.get(1), indexes.get(3))) {
                encoded.append(shiftReplacement(s, encode));
            } else {
                encoded.append(squareReplacement(defineIndexes(s)));
            }
        }
        return encoded.toString();
    }

    public void generateButtonClick() {
        gammaField.setText(String.valueOf(generateGamma(gammaWordField.getText())));
    }

    public void gammaEncodeButtonClick() {
        gammingResult.setText(gammaEncode(gammaWordField.getText(), Integer.parseInt(gammaField.getText())));
    }

    public void gammaDecodeButtonClick() {
        gammingResult.setText(gammaEncode(gammingResult.getText(), Integer.parseInt(gammaField.getText())));
    }

    public void inputCleaning() {
        gammaWordField.setText("");
        gammaField.setText("");
        gammingResult.setText("");
    }

    public void playfairEncodeButton() {
        playfairResult.setText(playfairEncode(playfairWord.getText().replaceAll("\\s", ""), true));
    }

    public void playfairDecodeButton() {
        playfairResult.setText(playfairEncode(playfairResult.getText().replaceAll("\\s", ""), false));
    }

    public void playfairMatrixGenerate(){
        printPrepared(randomMatrix(makeUnique(playfairKey.getText())));
    }
}