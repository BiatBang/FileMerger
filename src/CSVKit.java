import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVKit {
    public static void clean(HashMap<Integer, RowData> rMap, List<String> columns) {
        // if there are more columns than this record, use space to fill it
        for(RowData rd: rMap.values()) {
            for(String col: columns) {
                if(!rd.getProperty().containsKey(col)) {
                    List<String> newcol = new ArrayList();
                    List<String> newval = new ArrayList();
                    newcol.add(col);
                    newval.add(" ");
                    rd.setProperty(newcol, newval);
                }
            }
        }
    }

    public static void sort(HashMap<Integer, RowData> rMap, PriorityQueue<RowData> rQueue) {
        // put the records into the priorityqueue
        for(Map.Entry<Integer, RowData> entry: rMap.entrySet()) {
            rQueue.offer(entry.getValue());
        }
    }

    public static void write2Csv(PriorityQueue<RowData> rQueue, String output, List<String> columns) throws IOException {
        File outFile = new File(output);
        if(outFile.exists()) {
            System.out.println(Constant.DIV_LINE);
            System.out.println("There is already a file called: " + output + ", sorry I have to delete it...");
            outFile.delete();
        }
        CSVWriter csvWriter = null;
        try {
            outFile.createNewFile();
            // here, use the BOM to make the csv's charset utf-8
            FileOutputStream fos = new FileOutputStream(output);
            byte[] bom = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF };
            fos.write(bom);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            csvWriter = new CSVWriter(osw);

            String[] header = null;
            // if there is no record, just headers
            if(rQueue.size() <= 0){
                columns.add(0, Constant.ID_COL);
                header = columns.toArray(new String[0]);
                csvWriter.writeNext(header);
            }

            while(!rQueue.isEmpty()) {
                RowData rd = rQueue.poll();
                // since all records share one column set, let's see what's in 1st element
                if(header == null) {
                    header = rd.getPropNames().toArray(new String[0]);
                    csvWriter.writeNext(header);
                }
                String[] rowInfo = rd.toString().split(Constant.DIV_SYMB);
                csvWriter.writeNext(rowInfo);
            }

            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }
}
