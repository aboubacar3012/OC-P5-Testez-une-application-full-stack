package com.openclassrooms.starterjwt.payload.response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageResponseTest {

    @Test
    void messageResponseConstructor_WithValidMessage_ShouldCreateMessageResponse() {
        // Arrange
        String message = "Success";

        // Act
        MessageResponse messageResponse = new MessageResponse(message);

        // Assert
        assertThat(messageResponse.getMessage()).isEqualTo(message);
    }

    @Test
    void messageResponseConstructor_WithNullMessage_ShouldCreateMessageResponseWithNullMessage() {
        // Act
        MessageResponse messageResponse = new MessageResponse(null);

        // Assert
        assertThat(messageResponse.getMessage()).isNull();
    }
}
