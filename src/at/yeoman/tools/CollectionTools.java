
package at.yeoman.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionTools
{
	public static <K, E> void addToList(Map<K,List<E>> map, K key, E element)
	{
		getList(map, key).add(element);
	}
	
	public static <K, E> List<E> getList(Map<K,List<E>> map, K key)
	{
		List<E> list = map.get(key);
		
		if (list == null)
		{
			list = new ArrayList<>();
			map.put(key, list);
		}
		
		return list;
	}
	
	public static <K, E> void addToSet(Map<K,Set<E>> map, K key, E element)
	{
		getSet(map, key).add(element);
	}
	
	public static <K, E> Set<E> getSet(Map<K,Set<E>> map, K key)
	{
		Set<E> set = map.get(key);
		
		if (set == null)
		{
			set = new HashSet<>();
			map.put(key, set);
		}
		
		return set;
	}
}
