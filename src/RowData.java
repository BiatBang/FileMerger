import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowData implements Comparable<RowData> {
    private int id;
    /*
    * It's a trade off to store information into a HashMap
    * or specific variables to store columns
    *
    * Since it's extendable, a hashmap would be better
    * */
    private HashMap<String, String> propMap;

    public RowData(int id) {
        this.id = id;
        propMap = new HashMap();
    }

    public Map<String, String> getProperty() {
        return this.propMap;
    }

    /*
    * give the record some properties
    * @param keys list of new keys
    * @param values list of new values
    * */
    public void setProperty(List<String> keys, List<String> values) {
        for(int i = 0; i < keys.size(); i++) {
//            if(!columns.contains(keys.get(i))) columns.add(keys.get(i));
            if(i >= values.size()) {
                this.propMap.put(keys.get(i), "");
                continue;
            }
            this.propMap.put(keys.get(i), values.get(i));
        }
    }

    // when extract columns from a record
    public List<String> getPropNames() {
        List<String> propNames = new ArrayList();
        if(this.id >= 0) propNames.add(Constant.ID_COL);
        for(String prop: this.propMap.keySet()) propNames.add(prop);
        return propNames;
    }

    public int getId() {return this.id;}

    /*
    * override the compareTo to serve the use of priority queue
    * */
    @Override
    public int compareTo(RowData rowData) {
        return this.id - rowData.getId();
    }

    /*
    * When extract record into to csv
    * */
    @Override
    public String toString() {
        String res = String.valueOf(getId()) + Constant.DIV_SYMB;
        for(Map.Entry<String, String> entry: propMap.entrySet()) {
            res += (entry.getValue() + Constant.DIV_SYMB);
        }
        if(res.endsWith(Constant.DIV_SYMB)) res = res.substring(0, res.length() - Constant.DIV_SYMB.length());
        return res;
    }
}