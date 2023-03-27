package io.ylab.intensive.lesson03.transliterator;

public class TransliteratorUseArrayImpl implements Transliterator {
    @Override
    public String transliterate(String source) {
        String[][] transArr = {
                {"А", "A"}, {"Б", "B"}, {"В", "V"}, {"Г", "G"}, {"Д", "D"},
                {"Е", "E"}, {"Ё", "E"}, {"Ж", "ZH"}, {"З", "Z"}, {"И", "I"},
                {"Й", "I"}, {"К", "K"}, {"Л", "L"}, {"М", "M"}, {"Н", "N"},
                {"О", "O"}, {"П", "P"}, {"Р", "R"}, {"С", "S"}, {"Т", "T"},
                {"У", "U"}, {"Ф", "F"}, {"Х", "KH"}, {"Ц", "TS"}, {"Ч", "CH"},
                {"Ш", "SH"}, {"Щ", "SHCH"}, {"Ы", "Y"}, {"Ь", ""}, {"Ъ", "IE"},
                {"Э", "E"}, {"Ю", "IU"}, {"Я", "IA"}};
        StringBuilder newLine = new StringBuilder();
        boolean isFound;
        for (int i = 0; i < source.length(); i++) {
            char symbol = source.charAt(i);
            if (!Character.isLetter(symbol) || Character.isLowerCase(symbol)) {
                newLine.append(symbol);
                continue;
            }
            isFound = false;
            for (String[] strings : transArr) {
                if (strings[0].charAt(0) == symbol) {
                    newLine.append(strings[1]);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                newLine.append(symbol);
            }
        }
        return newLine.toString();
    }
}
