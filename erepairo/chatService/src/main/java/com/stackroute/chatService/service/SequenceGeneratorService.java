package com.stackroute.chatService.service;

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
	int generateSequence(String sequenceName);
}
