package net.icestone.estore.service;

import net.icestone.estore.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {

	@Override
	public void scheduleEmailConfirmation(User user) {
		System.out.println("scheduleEmailConfirmation");
		// Put user details into email queue
	}

}
