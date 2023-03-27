package io.ylab.intensive.lesson03.transliterator;
import java.util.HashMap;
import java.util.Map;

public class TransliteratorUseMapImpl implements Transliterator {
    @Override
    public String transliterate(String source) {
        Map<Character,String> transMap = new HashMap<>(33);
        transMap.put('A', "A");
        transMap.put('Б', "B");
        transMap.put('В', "V");
        transMap.put('Г', "G");
        transMap.put('Д', "D");
        transMap.put('Е', "E");
        transMap.put('Ё', "E");
        transMap.put('Ж', "ZH");
        transMap.put('З', "Z");
        transMap.put('И', "I");
        transMap.put('Й', "I");
        transMap.put('К', "K");
        transMap.put('Л', "L");
        transMap.put('М', "M");
        transMap.put('Н', "N");
        transMap.put('О', "O");
        transMap.put('П', "P");
        transMap.put('Р', "R");
        transMap.put('С', "S");
        transMap.put('Т', "T");
        transMap.put('У', "U");
        transMap.put('Ф', "F");
        transMap.put('Х', "KH");
        transMap.put('Ц', "TS");
        transMap.put('Ч', "CH");
        transMap.put('Ш', "SH");
        transMap.put('Щ', "SHCH");
        transMap.put('Ы', "Y");
        transMap.put('Ь', "");
        transMap.put('Ъ', "IE");
        transMap.put('Э', "E");
        transMap.put('Ю', "IU");
        transMap.put('Я', "IA");

        StringBuilder newLine = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char symbol = source.charAt(i);
            if (!Character.isLetter(symbol) || Character.isLowerCase(symbol)) {
                newLine.append(symbol);
            }else if (transMap.containsKey(symbol)){
                newLine.append(transMap.get(symbol));
            }
            else {
                newLine.append(symbol);
            }
        }
        return newLine.toString();
    }
}
