import java.util.HashMap;

public class FactoryMap {

    public HashMap<String, String> facMap = new HashMap();

    /*
    * the map stores extension and corresponding readers
    * key: extension name (no dot)
    * value: class name of the reader (case sensitive, of course)
    * */
    public FactoryMap() {
        this.facMap.put("html", "HTMLReader");
        this.facMap.put("csv", "CSVFileReader");
    }

    public String getClassName(String key) {
        if(facMap.containsKey(key)) return this.facMap.get(key);
        else return null;
    }
}
