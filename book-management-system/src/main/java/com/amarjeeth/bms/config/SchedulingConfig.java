package com.amarjeeth.bms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.amarjeeth.bms.service.BorrowingService;

@Configuration
@EnableScheduling
public class SchedulingConfig {

	private final BorrowingService borrowingService;

	@Autowired
	public SchedulingConfig(BorrowingService borrowingService) {
		this.borrowingService = borrowingService;
	}

	/**
	 * Update overdue status daily at midnight
	 */
	@Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
	public void updateOverdueStatus() {
		borrowingService.updateOverdueStatus();
	}
}