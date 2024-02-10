package org.nazar.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Test
    void testSend() {
        NotificationStrategy notificationStrategyMock = mock(NotificationStrategy.class);

        NotificationService notificationService = new NotificationServiceImpl();
        notificationService.send(notificationStrategyMock);

        verify(notificationStrategyMock).send();
    }
}
