import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public interface IReadable {
    /*
    * In order to make it extendable, define an interface
    * only provide a read method
    *
    * @param
    * */
    public void read(String input, HashMap<Integer, RowData> rQueue, List<String> columns) throws IOException;
}