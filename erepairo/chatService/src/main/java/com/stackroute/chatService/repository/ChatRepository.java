package com.stackroute.chatService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.chatService.entity.ChatDetails;

/**
 * The Interface ChatRepository.
 *
 * @author sushanth
 */
@Repository
public interface ChatRepository extends MongoRepository<ChatDetails, String> {

	/**
	 * Find by sender id.
	 *
	 * @param senderId the sender id
	 * @return the list
	 */
	List<ChatDetails> findBySenderId(int senderId);

	/**
	 * Find by sender id and receiver id.
	 *
	 * @param senderId   the sender id
	 * @param receiverId the receiver id
	 * @return the optional
	 */
	Optional<ChatDetails> findBySenderIdAndReceiverId(int senderId, int receiverId);

	/**
	 * Find by chat id and messages message id.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the optional
	 */
	Optional<ChatDetails> findByChatIdAndMessages_MessageId(String chatId, int messageId);

}
