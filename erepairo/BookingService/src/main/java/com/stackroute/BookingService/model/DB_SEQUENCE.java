package com.stackroute.BookingService.model;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "db_seq")
public class DB_SEQUENCE {


    @Id
    private String id;

    public DB_SEQUENCE() {
    }

    public DB_SEQUENCE(String id, int seq) {
        this.id = id;
        this.seq = seq;
    }

    private int seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
