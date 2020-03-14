import java.util.HashMap;

public class FactoryMap {

    public HashMap<String, String> facMap = new HashMap();

    public FactoryMap() {
        this.facMap.put("html", "HTMLReader");
        this.facMap.put("csv", "CSVFileReader");
    }

    public String getClassName(String key) {
        if(facMap.containsKey(key)) return this.facMap.get(key);
        else return null;
    }
}
