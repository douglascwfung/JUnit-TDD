package net.icestone.estore.data;

import java.util.HashMap;
import java.util.Map;

import net.icestone.estore.model.User;

public class UsersRepositoryImpl implements UsersRepository {
	
	Map<String, User> users = new HashMap<>();

	@Override
	public boolean save(User user) {
		
		boolean returnValue = false;
		
		if (!users.containsKey(user.getId())) {
			users.put(user.getId(), user);
			returnValue = true;
		}
		
		return returnValue;

	}

}
