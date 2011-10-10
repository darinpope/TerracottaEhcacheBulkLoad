package com.darinpope;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang.StringUtils;

import java.io.FileWriter;
import java.util.UUID;

public class FileGenerator {
    private static String filename;
    private static int rowCount;

    public static void main(String[] args) throws Exception {
        filename = System.getProperty("filename");
        rowCount = Integer.valueOf(System.getProperty("rowCount")).intValue();
        new FileGenerator();
    }

    public FileGenerator() throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(filename),'\t');
        try {
            for(int i=0;i<rowCount;i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(i));
                sb.append("###");
                sb.append(UUID.randomUUID().toString());
                sb.append("###");
                sb.append(UUID.randomUUID().toString());
                sb.append("###");
                sb.append(i%100 == 0 ? "true" : "false");
                writer.writeNext(StringUtils.split(sb.toString(),"###"));
                if(i%10000 == 0) {
                    System.out.println(i + " lines written");
                }
            }
        } finally {
            writer.close();
        }
    }
}
