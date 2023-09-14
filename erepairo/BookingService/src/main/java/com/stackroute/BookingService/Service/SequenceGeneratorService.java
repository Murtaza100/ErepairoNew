package com.stackroute.BookingService.Service;

import com.stackroute.BookingService.model.DB_SEQUENCE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int getseqno(String seqname) {
        Query query = new Query(Criteria.where("id").is(seqname));
        Update update = new Update().inc("seq", 1);
        DB_SEQUENCE counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true), DB_SEQUENCE.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
