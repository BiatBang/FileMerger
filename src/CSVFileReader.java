import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.io.Charsets;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class CSVFileReader implements IReadable {

    public CSVFileReader() {}

    @Override
    public void read(String input, HashMap<Integer, RowData> rMap, List<String> columns) throws IOException {
        // get headers
        List<String> headers = new ArrayList();
        System.out.println(Constant.DIV_LINE);
        System.out.println("Reading from " + input + "...");
        File htmlFile = new File(input);
        if(!htmlFile.exists()) {
            System.out.println("Sorry, this file: " + input + " doesn't exist. We'll skip this one");
            return;
        }

        // haha, this file had a name of CSVReader. turns out there's a first comer.
        CSVReader csvReader = null;

        try {
            csvReader = new CSVReader(new InputStreamReader(new FileInputStream(input), "UTF-8"));

            // headers, even you are not headers, how can I know?
            String[] line = csvReader.readNext();
            int idIndex = -1;
            for(int i = 0; i < line.length; i++) {
                if(line[i].equals(Constant.ID_COL)) {
                    if(idIndex == -1)  {
                        idIndex = i;
                        continue;
                    }
                    line[i] += "(?)";
                    System.out.println(Constant.MUL_ID_COL_IN_HEADER);
                }
                headers.add(line[i]);
                if(!columns.contains(line[i])) columns.add(line[i]);
            }
            // check if ID exists
            line = csvReader.readNext();
            if(idIndex != -1) {
                line:
                while(line != null) {
                    int id = -1;
                    List<String> rowInfo = new ArrayList();

                    for(int i = 0; i < line.length; i++) {
                        if(i == idIndex) {
                            // pick out the id;
                            try {
                                id = Integer.valueOf(line[i]);
                                if(rMap.containsKey(id)) {
                                    System.out.println("We already have this id: " + id);
                                    continue line;
                                }
                            } catch(Exception e) {
                                System.out.println("when extracting id: " + e);
                            }finally {
                                continue;
                            }
                        }
                        rowInfo.add(line[i]);
                    }
                    // make sure id col has value
                    if(id != -1) {
                        RowData rd = new RowData(id);
                        rd.setProperty(headers, rowInfo);
                        rMap.put(id, rd);
                    }
                    else System.out.println(Constant.NO_ID_IN_ROW);
                    line = csvReader.readNext();
                }
            }
            else System.out.println(Constant.NO_ID_COL_IN_HEADER + input);
        } catch(IOException ioe) {
            System.out.println(ioe);
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println(Constant.FILE_READ_FINISHED);
    }
}