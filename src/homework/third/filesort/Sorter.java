package homework.third.filesort;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sorter {
    private static final int BLOCK_SIZE = 30; // Размер блока для сортировки

    public File sortFile(File dataFile) throws IOException {
        String outputFileName = "src" + File.separator + "homework" + File.separator + "third" + File.separator + "filesort" + File.separator + "sorted.txt";
        List<Long> block = new ArrayList<>(BLOCK_SIZE);
        List<String> allBlocks = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            while (scanner.hasNextLong()) {
                Long stringFromFile = scanner.nextLong();
                block.add(stringFromFile);
                if (block.size() == BLOCK_SIZE) {
                    Collections.sort(block);
                    allBlocks.add(writeBlock(block));
                    block.clear();
                }
            }
            if (!block.isEmpty()) {
                Collections.sort(block);
                allBlocks.add(writeBlock(block));
            }
        }

        while (allBlocks.size() > 1) {
            List<String> mergedBlocks = new ArrayList<>();
            for (int i = 0; i < allBlocks.size(); i += 2) {
                String mergedBlock = mergeBlocks(allBlocks.get(i), i + 1 < allBlocks.size() ? allBlocks.get(i + 1) : null);
                mergedBlocks.add(mergedBlock);
            }
            allBlocks = mergedBlocks;
        }

        File outputFile = new File(outputFileName);
        try (FileInputStream fileInputStream = new FileInputStream(allBlocks.get(0));
             Scanner scanner = new Scanner(fileInputStream)) {
            try (PrintWriter pw = new PrintWriter(outputFileName)) {
                while (scanner.hasNextLong()) {
                    Long stringFromFile = scanner.nextLong();
                    pw.println(stringFromFile);
                }
                pw.flush();
            }
        }
        return outputFile;
    }

    private static String writeBlock(List<Long> block) throws IOException {
        File file = File.createTempFile("block", ".txt");
        file.deleteOnExit();
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Long element : block) {
                pw.println(element);
            }
            pw.flush();
        }
        return file.getAbsolutePath();
    }

    private static String mergeBlocks(String block1Path, String block2Path) throws IOException {
        if (block2Path == null) {
            return block1Path;
        }
        List<Long> mergedBlock = new ArrayList<>(2 * BLOCK_SIZE);
        try (FileInputStream fileInputStream = new FileInputStream(block1Path);
             Scanner scanner_block1 = new Scanner(fileInputStream);
             FileInputStream fileInputStream2 = new FileInputStream(block2Path);
             Scanner scanner_block2 = new Scanner(fileInputStream2)) {
            Long value1 = scanner_block1.hasNextLong() ? scanner_block1.nextLong() : null;
            Long value2 = scanner_block2.hasNextLong() ? scanner_block2.nextLong() : null;
            while (value1 != null || value2 != null) {
                if (value1 == null) {
                    mergedBlock.add(value2);
                    value2 = scanner_block2.hasNextLong() ? scanner_block2.nextLong() : null;
                } else if (value2 == null) {
                    mergedBlock.add(value1);
                    value1 = scanner_block1.hasNextLong() ? scanner_block1.nextLong() : null;
                } else if (value1 < value2) {
                    mergedBlock.add(value1);
                    value1 = scanner_block1.hasNextLong() ? scanner_block1.nextLong() : null;
                } else {
                    mergedBlock.add(value2);
                    value2 = scanner_block2.hasNextLong() ? scanner_block2.nextLong() : null;
                }
            }
            String mergedBlockPath = writeBlock(mergedBlock);
            mergedBlock.clear();
            return mergedBlockPath;
        }
    }
}

