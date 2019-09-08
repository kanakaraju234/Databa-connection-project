package com.demo.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.LoadType;


public class Store{

	static 
	{
      ObjectifyService.register(UsersJDO.class);
      ObjectifyService.register(Author.class);
    }
    
	public static Objectify ofy()
    {
        return ObjectifyService.ofy();
    }

	public static <T> LoadType<T> get(Class<T> claz)
	{
		return Store.ofy().load().type(claz);
	}

	public static <T> T get(Class<T> claz, String id)
	{
		return Store.ofy().load().type(claz).id(id).now();
	}
	
	public static <T> T get(Class<T> claz, Long id)
	{
		return Store.ofy().load().type(claz).id(id).now();
	}

	public static <T> T get(Class<T> claz, String id, Boolean cache)
	{
		return Store.ofy().cache(cache).load().type(claz).id(id).now();
	}

	public static <T> T getWithStatus(Class<T> claz, String id, Boolean status)
	{
		T object = Store.ofy().load().type(claz).id(id).now();
		try 
		{
			if(object != null)
			{
				if((Boolean)Class.forName(object.getClass().getName()).getDeclaredMethod("getStatus").invoke(object).equals(status))
				{
					return object;
				}
			}
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> getByKeys(Class<T> claz, Collection<String> ids)
	{
		List<T> l = new ArrayList<>();
		Collection<T> collection = Store.ofy().load().type(claz).ids(ids).values();
		if(collection != null){
			for (T t : collection) {
				l.add(t);
			}
		}
		return l;
	}

	public static <T> List<T> getByKeysWithStatus(Class<T> claz, Collection<String> ids, Boolean status)
	{
		List<T> returnList = new ArrayList<>();
		Collection<T> collection = Store.ofy().load().type(claz).ids(ids).values();
		if(collection != null){
			for (T t : collection) {
				try 
				{
					if((Boolean)Class.forName(t.getClass().getName()).getDeclaredMethod("getStatus").invoke(t))
					{
						returnList.add(t);
					}
				} 
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return returnList;
	}
	
	public static <T> Map<String, T> getByKeysMap(Class<T> claz, Collection<String> ids)
	{
		return Store.ofy().load().type(claz).ids(ids);
	}

	public static <T> void save(T entity)
	{
		Store.ofy().save().entity(entity).now();
	}

	public static <T> void saveall(Collection<T> entities)
	{
		Store.ofy().save().entities(entities).now();
	}

	public static <T> void delete(Key<T> key)
	{
		Store.ofy().delete().key(key).now();
	}
	
	public static <T> void deleteall(Collection<Key<T>> keys)
	{
		Store.ofy().delete().keys(keys).now();
	}
	
}
