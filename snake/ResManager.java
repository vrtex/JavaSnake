import java.util.TreeMap;

public class ResManager<T>
{
    private TreeMap<String, T> content = new TreeMap<>();

    public ResManager()
    {

    }

    public boolean insert(String id, T toAdd)
    {
        if(content.containsKey(id)) return false;
        content.put(id, toAdd);
        return true;
    }

    public T get(String id)
    {
        if(!content.containsKey(id)) return null;
        return content.get(id);
    }

    public boolean contains(String id)
    {
        return content.containsKey(id);
    }

}