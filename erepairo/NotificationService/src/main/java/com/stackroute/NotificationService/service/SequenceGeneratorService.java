package com.stackroute.NotificationService.service;

/**
 * The Interface SequenceGeneratorService.
 *
 * @author sushanth
 */
public interface SequenceGeneratorService {

	/**
	 * Generate sequence.
	 *
	 * @param sequenceName the sequence name
	 * @return the long
	 */
	long generateSequence(String sequenceName);
}
