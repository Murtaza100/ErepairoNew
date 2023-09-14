package com.stackroute.BookingService.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {
    private String MessageId;
    private String Message;
    private String MessageDate;
}
