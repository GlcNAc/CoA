package prj.coa.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.devin.db.DbMapUtil;

public class Collections<T> {
	public static <T> void copy(List<? super T> dest, List<? extends T> src, boolean append) {
		if ((src == null) || (dest == null)) {
			throw new NullPointerException("src or dest must not null");
		}
		if (!(append)) {
			dest.clear();
		}
		for (T s : src)
			dest.add(s);
	}

	public static <T> void copy(List<? super T> dest, List<? extends T> src) {
		copy(dest, src, false);
	}

	public static <T> List<T> arrayToList(T[] arrays) {
		List<T> arrayList = new ArrayList<T>();
		for (T t : arrays) {
			arrayList.add(t);
		}
		return arrayList;
	}

	public static List<Integer> arrayToList(int[] arrays) {
		int[] arrayOfInt;
		List<Integer> arrayList = new ArrayList<Integer>();
		int j = (arrayOfInt = arrays).length;
		for (int i = 0; i < j; ++i) {
			Integer t = Integer.valueOf(arrayOfInt[i]);
			arrayList.add(t);
		}
		return arrayList;
	}

	public static <T> void arrayToList(T[] arrays, List<T> list) {
		for (T t : arrays)
			list.add(t);
	}

	public static <K, V> List<V> mapToList(Map<K, V> map) {
		if (map == null) {
			return null;
		}
		Iterator<Entry<K, V>> iter = map.entrySet().iterator();
		List<V> list = new ArrayList<V>();
		while (iter.hasNext()) {
			list.add(iter.next().getValue());
		}
		return list;
	}

	public static String arrayToString(Object[] args, char separate) {
		if (args == null) {
			return "[]";
		}
		StringBuilder str = new StringBuilder();
		int count = args.length;
		str.append("[");
		for (int i = 0; i < count; ++i) {
			if (i > 0) {
				str.append(separate);
			}
			str.append(args[i]);
		}
		str.append("]");
		return str.toString();
	}

	public static String arrayToString(int[] args, char separate) {
		if (args == null) {
			return "[]";
		}
		StringBuilder str = new StringBuilder();
		int count = args.length;
		str.append("[");
		for (int i = 0; i < count; ++i) {
			if (i > 0) {
				str.append(separate);
			}
			str.append(args[i]);
		}
		str.append("]");
		return str.toString();
	}

	public static <T> void addAllNoEquals(Collection<T> src, Collection<T> dst) {
		if ((src == null) || (dst == null)) {
			return;
		}
		Iterator<T> dstIterator = dst.iterator();
		while (dstIterator.hasNext()) {
			T d = dstIterator.next();
			if (!(src.contains(d)))
				src.add(d);
		}
	}

	public static <T> void addAllNoEquals(Collection<T> dst, T src) {
		if ((src == null) || (dst == null)) {
			return;
		}
		if (!(dst.contains(src)))
			dst.add(src);
	}

	public static List<Map<String, Object>> reOrderList(List<Map<String, Object>> dta, List<Integer> order,
			String key) {
		if ((dta == null) || (dta.size() == 0) || (key == null)) {
			return dta;
		}

		Map<Integer, Map<String, Object>> maps = new HashMap<Integer, Map<String, Object>>();
		List<Map<String, Object>> ordDta = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < dta.size(); i++) {
			Map<String, Object> m = dta.get(i);
			Integer k = Integer.valueOf(DbMapUtil.getValue(m, key, 0));
			maps.put(k, m);
		}

		for (int i = 0; i < order.size(); i++) {
			Object k = order.get(i);
			Map<String, Object> m = maps.get(k);
			if (m != null) {
				ordDta.add(m);
			}
		}

		return ordDta;
	}

	public static List<Integer> stringToIntList(String src, String split) {
		if (src == null) {
			return new LinkedList<Integer>();
		}
		String[] srcs = src.split(split);
		List<Integer> list = new ArrayList<Integer>();
		if ((srcs != null) && (srcs.length > 0))
			for (int i = 0; i < srcs.length; ++i)
				try {
					list.add(Integer.valueOf(Integer.parseInt(srcs[i])));
				} catch (Exception localException) {
				}
		return list;
	}
}