
package at.yeoman.mutabor.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrictMap<K, V>
{
	private final HashMap<K,V> m_data;
	
	public StrictMap()
	{
		m_data = new HashMap<>();
	}
	
	public StrictMap(int initialCapacity)
	{
		m_data = new HashMap<>(initialCapacity);
	}
	
	public StrictMap(Map<? extends K,? extends V> map)
	{
		for (Map.Entry<?,?> entry : map.entrySet())
		{
			if (entry.getKey() == null || entry.getValue() == null)
			{
				throw new NullPointerException("Key or value == null - key: [" + entry.getKey() + "], value: [" + entry.getValue() + "]");
			}
		}
		
		m_data = new HashMap<>(map);
	}
	
	public StrictMap(StrictMap<? extends K,? extends V> map)
	{
		m_data = new HashMap<>(map.m_data);
	}
	
	public StrictMap(int initialCapacity, float loadFactor)
	{
		m_data = new HashMap<>(initialCapacity, loadFactor);
	}
	
	/**
	 * @param key
	 * @return the value found for the key
	 * @throws IllegalArgumentException
	 *             if no value was found
	 */
	public V get(K key)
	{
		V result = m_data.get(key);
		if (result != null)
		{
			return result;
		}
		else
		{
			throw new IllegalArgumentException("No value found for key " + key);
		}
	}
	
	/**
	 * @param key
	 * @return An option containing the value found for the key, else an empty
	 *         option
	 */
	public Option<V> getOption(K key)
	{
		V result = m_data.get(key);
		if (result != null)
		{
			return Option.some(result);
		}
		else
		{
			return Option.none();
		}
	}
	
	public V getOrNull(K key)
	{
		return m_data.get(key);
	}
	
	/**
	 * Puts an value into the map for a new key. Does not replace values but
	 * throws if a value already exists for the key.
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 *             if a value already exists for the key
	 * @throws NullPointerException
	 *             if any of key, value is null
	 */
	public void put(K key, V value)
	{
		if (key == null || value == null)
		{
			throw new NullPointerException("Key or value == null - key: [" + key + "], value: [" + value + "]");
		}
		
		if (m_data.containsKey(key))
		{
			throw new IllegalArgumentException("Overwrite attempt for key [" + key + "]");
		}
		
		m_data.put(key, value);
	}
	
	/**
	 * @param key
	 * @param value
	 * @return the An option containing the original value if replaced, else an
	 *         empty option.
	 */
	public Option<V> putOrReplace(K key, V value)
	{
		if (key == null || value == null)
		{
			throw new NullPointerException("Key or value == null - key: [" + key + "], value: [" + value + "]");
		}
		
		V result = m_data.put(key, value);
		
		if (result != null)
		{
			return Option.some(result);
		}
		else
		{
			return Option.none();
		}
	}
	
	public int size()
	{
		return m_data.size();
	}
	
	public boolean isEmpty()
	{
		return m_data.isEmpty();
	}
	
	public boolean containsKey(K key)
	{
		return m_data.containsKey(key);
	}
	
	public boolean containsValue(V value)
	{
		return m_data.containsValue(value);
	}
	
	public V remove(K key)
	{
		return m_data.remove(key);
	}
	
	public void putAll(Map<? extends K,? extends V> map)
	{
		for (Map.Entry<?,?> entry : map.entrySet())
		{
			if (entry.getKey() == null || entry.getValue() == null)
			{
				throw new NullPointerException("Key or value == null - key: [" + entry.getKey() + "], value: [" + entry.getValue() + "]");
			}
			
			if (m_data.containsKey(entry.getKey()))
			{
				throw new IllegalArgumentException("Overwrite attempt for key [" + entry.getKey() + "]");
			}
		}
		
		m_data.putAll(map);
	}
	
	public void clear()
	{
		m_data.clear();
	}
	
	public Set<K> keySet()
	{
		return m_data.keySet();
	}
	
	public Collection<V> values()
	{
		return m_data.values();
	}
	
	public Set<java.util.Map.Entry<K,V>> entrySet()
	{
		return m_data.entrySet();
	}
	
	@Override
	public String toString()
	{
		return "StrictMap [m_data=" + m_data + "]";
	}
	
	public static <K, E> void addToList(StrictMap<K,List<E>> map, K key, E element)
	{
		getList(map, key).add(element);
	}
	
	public static <K, E> List<E> getList(StrictMap<K,List<E>> map, K key)
	{
		List<E> list = map.getOrNull(key);
		
		if (list == null)
		{
			list = new ArrayList<>();
			map.put(key, list);
		}
		
		return list;
	}
	
	public static <K, E> void addToSet(StrictMap<K,Set<E>> map, K key, E element)
	{
		getSet(map, key).add(element);
	}
	
	public static <K, E> Set<E> getSet(StrictMap<K,Set<E>> map, K key)
	{
		Set<E> set = map.getOrNull(key);
		
		if (set == null)
		{
			set = new HashSet<>();
			map.put(key, set);
		}
		
		return set;
	}
}
