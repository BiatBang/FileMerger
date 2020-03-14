import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ReaderFactory {
    public FactoryMap facMap;

    public ReaderFactory() {
        facMap = new FactoryMap();
    }

    public String getExtension(String filename) {
        String ext = "";
        int dot = filename.lastIndexOf('.');
        if (dot > 0) {
            ext = filename.substring(dot + 1);
        }
        return ext;
    }

    public IReadable getReader(String filename) {
        String ext = getExtension(filename);
        try {
            String className = facMap.getClassName(ext);
            if(className != null) return (IReadable) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(Constant.DIV_LINE);
        System.out.println("Sorry, this file: " + filename + " has an invalid extension");
        return null;
    }
}
