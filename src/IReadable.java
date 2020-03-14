import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public interface IReadable {
    /*
    * In order to make it extendable, define an interface
    * only provide a read method
    *
    * @param input the input file
    * @param rMap the hashmap of id -> info
    * @param columns largest columns
    *
    * */
    public void read(String input, HashMap<Integer, RowData> rMap, List<String> columns) throws IOException;
}