package ru.will0376.igui;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuppressWarnings("unchecked")
public class Switches {
	public static Map<String, Switches> switchesMap = new HashMap<>();

	public static Switches getByName(String modid) {
		if (!switchesMap.containsKey(modid)) {
			Switches value = new Switches();
			switchesMap.put(modid, value);
			return value;
		} else return switchesMap.get(modid);
	}

	public static boolean findBoolean(String name, Boolean value) {
		return findWithCast(name, value) == value;
	}

	public static <T> T findWithCast(String name, T value) {
		return (T) findWithCast(value.getClass(), name);
	}

	public static <T> T findWithCast(Class<T> cast, String name) {
		try {
			for (Map.Entry<String, Switches> entry : switchesMap.entrySet()) {
				Switches switches = entry.getValue();
				for (Field declaredField : switches.getClass().getDeclaredFields()) {
					if (declaredField.getName().equals(name)) return (T) declaredField.get(switches);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
