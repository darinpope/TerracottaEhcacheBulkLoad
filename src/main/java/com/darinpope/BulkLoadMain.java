package com.darinpope;


import au.com.bytecode.opencsv.CSVReader;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BulkLoadMain {

    private static boolean batchLoad = false;
    private static String loaderFile = "business.txt";
    private static int batchSize = 1024;

    public static void main(String[] args) throws Exception {
        batchLoad = Boolean.valueOf(System.getProperty("batchLoad"));
        String tempLoaderFile = System.getProperty("loaderFile");
        if(StringUtils.isNotBlank(tempLoaderFile)) {
            loaderFile = tempLoaderFile;
        }
        String tempBatchSize = System.getProperty("batchSize");
        if(StringUtils.isNotBlank(tempBatchSize)) {
            batchSize = Integer.valueOf(tempBatchSize).intValue();
        }

        new BulkLoadMain();
    }

    public BulkLoadMain() throws Exception {
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("BusinessCache");

        DisplayCacheSize(cache);
        cache.setNodeBulkLoadEnabled(true);
        LoadBusinessRecordsBatch(cache);
        cache.setNodeBulkLoadEnabled(false);
        DisplayCacheSize(cache);
        cacheManager.shutdown();
    }

    public void DisplayCacheSize(Cache cache) {
        System.out.println("Current cache size = " + cache.getSize());
    }

    public void LoadBusinessRecordsBatch(Cache cache) throws Exception {
        System.out.println("Loading records from: '" + loaderFile + "'");

        CSVReader reader = new CSVReader(new FileReader(loaderFile),'\t');
        try {
            Business business;
            Element element;
            int businessCount = 0;
            long startTime, endTime;

            if(batchLoad) {
                List<Element> businesses = new ArrayList<Element>(batchSize);
                startTime = System.nanoTime();
                while ((business = readBusiness(reader)) != null) {
                    businessCount++;
                    element = new Element(business.getId(), business);
                    businesses.add(element);

                    if (businessCount % batchSize == 0) {
                        cache.putAll(businesses);
                        businesses.clear();
                        System.out.print('.');
                    }
                }

                // insert the final batch
                if (!businesses.isEmpty()) {
                    cache.putAll(businesses);
                }

                endTime = System.nanoTime();

                double secondsDuration = (endTime - startTime) / (double) 1000000000;

                System.out.println("\nInsertion time for batch mode: "
                    + businessCount
                    + " records = "
                    + secondsDuration
                    + " seconds ("
                    + businessCount
                    / secondsDuration
                    + " objects/sec)");
            } else {
                startTime = System.nanoTime();
                while ((business = readBusiness(reader)) != null) {
                    businessCount++;
                    element = new Element(business.getId(), business);
                    cache.put(element);
                }
                endTime = System.nanoTime();

                double secondsDuration = (endTime - startTime) / (double) 1000000000;

                System.out.println("\nInsertion time for normal mode: "
                    + businessCount
                    + " records = "
                    + secondsDuration
                    + " seconds ("
                    + businessCount
                    / secondsDuration
                    + " objects/sec)");
            }
        } finally {
            reader.close();
        }
    }

    private Business readBusiness(CSVReader reader) throws IOException {
        String[] asPart = reader.readNext();
        if (asPart == null) {
            return null;
        }

        int ofPart = 0;

        int id = Integer.parseInt(asPart[ofPart++]);
        String companyName = asPart[ofPart++];
        String companyTag = asPart[ofPart++];
        Date creationDate = new Date();
        boolean active = Boolean.parseBoolean(asPart[ofPart++]);

        return new Business(id,companyName,companyTag,creationDate,active);
    }
}
