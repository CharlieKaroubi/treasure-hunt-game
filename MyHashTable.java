import java.util.LinkedList;
import java.util.HashSet;

public class MyHashTable<K,V>{
    private LinkedList<V>[] table;
    private HashSet<K> keySet;


    @SuppressWarnings("unchecked")
    public MyHashTable(){
      table = new LinkedList[9999999];
      keySet = new HashSet<K>();
    }

    public void put(K key, V value){
      int hashCode = key.hashCode();

      if(keySet.contains(key)){
        table[hashCode].add(value);
      }
      else{
        keySet.add(key);

        table[hashCode] = new LinkedList<V>();
        table[hashCode].add(value);

      }
    }

    public LinkedList<V> get(K key){
      int hashCode = key.hashCode();

      if(keySet.contains(key)){
        return table[hashCode];
      }
      else{
        return null;
      }
    }

  public HashSet<K> keySet(){
    return keySet;
  }

}

