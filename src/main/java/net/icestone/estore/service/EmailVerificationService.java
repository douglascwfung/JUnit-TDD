package net.icestone.estore.service;

import net.icestone.estore.model.User;

public interface EmailVerificationService {
	void scheduleEmailConfirmation(User user);
}
