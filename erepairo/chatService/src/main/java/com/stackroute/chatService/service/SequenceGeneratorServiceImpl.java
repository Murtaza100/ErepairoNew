package com.stackroute.chatService.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.stackroute.chatService.entity.DatabaseSequence;

/**
 * The Class SequenceGeneratorServiceImpl.
 *
 * @author sushanth
 */
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

	/** The mongo operations. */
	@Autowired
	private MongoOperations mongoOperations;

	/**
	 * Generate sequence.
	 *
	 * @param sequenceName the sequence name
	 * @return the long
	 */
	@Override
	public int generateSequence(String sequenceName) {
		DatabaseSequence databaseSequence = mongoOperations.findAndModify(query(where("_id").is(sequenceName)),
				new Update().inc("seq", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);
		return !Objects.isNull(databaseSequence) ? databaseSequence.getSeq() : 1;

	}
}
