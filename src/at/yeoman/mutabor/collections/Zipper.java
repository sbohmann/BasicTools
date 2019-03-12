
package at.yeoman.mutabor.collections;

import java.util.ArrayList;
import java.util.List;

public class Zipper {
	public static <E> List<Zipped<E>> zipped(List<E> list) {
		List<Zipped<E>> result = new ArrayList<>(list.size());
		
		final int size = list.size();
		for (int index = 0; index < size; ++index)
		{
			result.add(new Zipped<>(list.get(index), index, index == 0, index == size - 1));
		}
		
		return result;
	}
}
