package com.stackroute.chatService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;

}