import org.apache.commons.io.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class HTMLReader implements IReadable {

    public HTMLReader() {}

    @Override
    public void read(String input, HashMap<Integer, RowData> rMap, List<String> columns) throws IOException {

        List<String> headers = new ArrayList();
        System.out.println(Constant.DIV_LINE);
        System.out.println("Reading from " + input + "...");
        File htmlFile = new File(input);
        if(!htmlFile.exists()) {
            System.out.println("Sorry, this file: " + input + " doesn't exist. We'll skip this one");
            return;
        }
        Document doc = Jsoup.parse(htmlFile, String.valueOf(Charsets.UTF_8));

        // get headers
        // hah, guess i shouldn't put "th" and "tr" here
        Elements ths = doc.getElementsByTag(Constant.HTML_TABLE_HEAD_TAG);
        // if there is no ID col, or there is even no headers, no point moving forward
        int idIndex = -1;
        for(int i = 0; i < ths.size(); i++) {
            String header = ths.get(i).text();
            if(header.equals(Constant.ID_COL)) {
                if(idIndex == -1)  {
                    idIndex = i;
                    continue;
                }
                header += "(?)";
                System.out.println(Constant.MUL_ID_COL_IN_HEADER);
            }
            headers.add(header);
            if(!columns.contains(header)) columns.add(header);
        }
        // only handle when there is valid ID
        if(idIndex != -1) {
            Elements trs = doc.getElementsByTag(Constant.HTML_TABLE_ROW_TAG);
            // iterate the rows
            line:
            for(Element tr: trs) {
                // first row is header (maybe)
                if(tr.select("th").size() > 0) {
                    continue;
                }
                // pick out the cell data
                List<String> rowInfo = new ArrayList();
                int id = -1;
                for(int i = 0; i < tr.children().size(); i++) {
                    if(i == idIndex) {
                        // pick out the id;
                        try {
                            id = Integer.valueOf(tr.children().get(i).text());
                            if(rMap.containsKey(id)) {
                                System.out.println("We already have this id: " + id);
                                continue line;
                            }
                        } catch(Exception e) {
                            System.out.println(e);
                        }finally {
                            continue;
                        }
                    }
                    rowInfo.add(tr.children().get(i).text());
                }

                if(id != -1) {
                    RowData rd = new RowData(id);
                    rd.setProperty(headers, rowInfo);
                    rMap.put(id, rd);
                }
                else System.out.println(Constant.NO_ID_IN_ROW);
            }
        }
        else System.out.println(Constant.NO_ID_COL_IN_HEADER + input);

        System.out.println(Constant.FILE_READ_FINISHED);
    }
}