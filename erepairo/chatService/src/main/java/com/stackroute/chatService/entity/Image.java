package com.stackroute.chatService.entity;

import org.bson.types.Binary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

	private String id;
	private String title;
	private Binary data;
}
