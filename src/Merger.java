import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Merger {
    private String[] inputFiles;
    private String[] args;
    private String outputFile;
    private List<String> columns;
    private PriorityQueue<RowData> rQueue;
    private HashMap<Integer, RowData> rMap;

    /*
     *  the main handler of reading and writing
     *
     * @param inputFiles the name list of input files
     * @param outputFile the name of output file
     * */
    public Merger(String[] args, String outputFile) {
        this.args = args;
        this.outputFile = outputFile;
        this.columns = new ArrayList();
        this.rQueue = new PriorityQueue();
        this.rMap = new HashMap();
    }

    public void getFileNames() {
        File file = new File(args[0]);
        List<String> nameList = new ArrayList();
        for(String fname: file.list()) {
            nameList.add(args[0] + "/" + fname);
        }
        inputFiles = nameList.toArray(new String[0]);

//        try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {
//
//            List<String> result = walk.filter(Files::isRegularFile)
//                    .map(x -> x.toString()).collect(Collectors.toList());
//            this.inputFiles = result.toArray(new String[0]);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /*
     *  a general function to merge any kinds of files
     *  no return value. just create a new file
     * */
    public void merge() throws Exception {
        getFileNames();
        /*
         * iterate the input files,
         * in case in different files, there are different columns,
         * and we don't want to miss any column,
         * we use a largest set of columns to be the final columns
         *
         * */
        for (String input : inputFiles) {
            // check it's extension and send to respective handler
            IReadable reader = new ReaderFactory().getReader(input);
            if (reader != null) {
                reader.read(input, rMap, columns);
            }
        }

        // here we have a ordered queue of rows, we want to fill in the blanks
        // since columns may differ in different files
        CSVKit.clean(rMap, columns);

        // sort by id
        CSVKit.sort(rMap, rQueue);

        // then, we write final results to the csv file
        CSVKit.write2Csv(rQueue, outputFile, columns);
    }
}