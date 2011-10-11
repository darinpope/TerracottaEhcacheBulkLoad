package com.darinpope;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang.StringUtils;

import java.io.FileWriter;
import java.util.UUID;

public class FileGenerator {
    private static String filename;
    private static int rowCount;
    private static int numberOfFiles;
    private static String filePath;

    public static void main(String[] args) throws Exception {
        filename = System.getProperty("filename");
        rowCount = Integer.valueOf(System.getProperty("rowCount")).intValue();
        numberOfFiles = Integer.valueOf(System.getProperty("numberOfFiles")).intValue();
        filePath = System.getProperty("filePath");
        new FileGenerator();
    }

    public FileGenerator() throws Exception {
        int totalElements = 1;
        for(int j=0;j<numberOfFiles;j++) {
            String newFileName = j + "-" + filename;
            if(StringUtils.isNotBlank(filePath)) {
                newFileName = filePath + newFileName;
            }
            CSVWriter writer = new CSVWriter(new FileWriter(newFileName),'\t');
            try {
                for(int i=0;i<rowCount;i++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(totalElements));
                    sb.append("###");
                    sb.append(UUID.randomUUID().toString());
                    sb.append("###");
                    sb.append(UUID.randomUUID().toString());
                    sb.append("###");
                    sb.append(i%100 == 0 ? "true" : "false");
                    writer.writeNext(StringUtils.split(sb.toString(),"###"));
                    totalElements++;
                    if(i%10000 == 0) {
                        System.out.println(totalElements + " lines written");
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
