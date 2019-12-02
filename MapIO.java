
package streamercollector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapIO {
    private HashMap<String, String> hmap = null;
    private String path;
    
    MapIO(String path){
        this.path = path;
        deserializeMap();
    }
    


    void deserializeMap(){
        try
        {
           FileInputStream fis = new FileInputStream(path);
           ObjectInputStream ois = new ObjectInputStream(fis);
           hmap = (HashMap) ois.readObject();
           ois.close();
           fis.close();
        }catch(IOException ioe)
        {
           ioe.printStackTrace();
           return;
        }catch(ClassNotFoundException c)
        {
           System.out.println("Class not found");
           c.printStackTrace();
           return;
        }
    }
    
    void displayMap(){
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        int  i = 1;
        while(iterator.hasNext()) {
           Map.Entry mentry = (Map.Entry)iterator.next();
           System.out.print(i+": "+"key: "+ mentry.getKey() + " & Value: ");
           System.out.println(mentry.getValue());
           i++;
        }
        System.out.println();
    }
    
    void serializeMap(){
        try {
              FileOutputStream fos = new FileOutputStream(path);
              ObjectOutputStream oos = new ObjectOutputStream(fos);
              oos.writeObject(hmap);
              oos.close();
              fos.close();
              System.out.println("Serialized HashMap data is saved in: "+path);
          }
          catch(IOException ioe){
              ioe.printStackTrace();
          }
    }
    
    void put(String key, String value){
        hmap.put(key, value);
    }
    
    String getValue(String key){
        return hmap.get(key);
    }
    
    boolean containsValue(String value){
        return hmap.containsValue(value);
    }
    
    boolean containsKey(String key){
        return hmap.containsKey(key);
    }
    
    int size(){
        return hmap.size();
    }
}
