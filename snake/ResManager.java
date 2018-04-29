import java.util.NoSuchElementException;
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

    public T get(String id) throws NoSuchElementException
    {
        if(!content.containsKey(id))
        {
        	System.out.printf("Element not found: %s\n", id);
            throw new NoSuchElementException();
        }
        return content.get(id);
    }

    public boolean contains(String id)
    {
        return content.containsKey(id);
    }
    
    public boolean remove(String id)
	{
		if(!contains(id)) return false;
		content.remove(id);
		return true;
	}

}