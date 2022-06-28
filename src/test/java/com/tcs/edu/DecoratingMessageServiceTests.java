package com.tcs.edu;

import com.tcs.edu.decorator.OrderDoublingProcessor;
import com.tcs.edu.decorator.PagingDecorator;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.*;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.service.DecoratingMessageService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

@DisplayName("Decorating message service tests")
public class DecoratingMessageServiceTests {

    DecoratingMessageService sut;
    DecoratedMessage currentMessage;
    UUID uuid;
    Collection<DecoratedMessage> allMessages;

    @BeforeEach
    public void SetUp() {
        sut = new DecoratingMessageService(
                new HashMapMessageRepository(),
                new OrderDoublingProcessor(),
                new TimestampMessageDecorator(),
                new PagingDecorator());
//        currentMessage = null;
//        uuid = null;
//        allMessages = null;
    }

    @AfterEach
    public void TearDown() {
        sut = null;
        currentMessage = null;
        uuid = null;
        allMessages = null;
    }

    @Nested
    @DisplayName("Process Message Method")
    class ProcessMessage {

        @Test
        @DisplayName("Should Decorate Save And Return By Key From HashMap When Is Valid")
        public void ShouldDecorateSaveAndReturnMessageByKeyFromHashMapWhenIsValid() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            currentMessage = sut.findByPrimaryKey(sut.processMessage(message1));
            System.out.println(currentMessage);
            //

            //Then
            assertThat(currentMessage).isNotNull()
                    .extracting("level", "message", "id")
                    .doesNotContainNull();
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return By Key From HashMap When Message Is Null")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, null);
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                currentMessage = sut.findByPrimaryKey(sut.processMessage(message1));
                System.out.println(currentMessage);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return By Key From HashMap When Severity Is Null")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenSeverityIsNull() throws LogException {
            //Given
            Message message1 = new Message(null, "Hello world!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                currentMessage = sut.findByPrimaryKey(sut.processMessage(message1));
                System.out.println(currentMessage);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return By Key From HashMap When Whole Message Is Null")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenWholeMessageIsNull() throws LogException {
            //Given
            Message message1 = null;
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                currentMessage = sut.findByPrimaryKey(sut.processMessage(message1));
                System.out.println(currentMessage);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return By Key From HashMap When Whole Message Is Empty")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenWholeMessageIsEmpty() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                currentMessage = sut.findByPrimaryKey(sut.processMessage(message1));
                System.out.println(currentMessage);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
    }

    @Nested
    @DisplayName("Process Messages Method")
    class ProcessMessages {

        @Test
        @DisplayName("Should Decorate Save And Return All Messages From HashMap When Is Valid")
        public void ShouldDecorateSaveAndReturnAllMessagesFromHashMapWhenIsValid() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.REGULAR, "Ciao mondo!");
            //

            //When
            sut.processMessages(message1, message2);
            allMessages = sut.findAll();
            for (DecoratedMessage currentProcessedMessage : allMessages) {
                currentMessage = currentProcessedMessage;
                System.out.println(currentMessage);
            }
            //

            //Then
            assertThat(allMessages).isNotNull()
                    .extracting("level", "message", "id")
                    .contains(tuple(currentMessage.getLevel(), currentMessage.getMessage(), currentMessage.getId()))
                    .doesNotContainNull()
                    .hasSize(2);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages From HashMap When Message Is Null")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, null);
            Message message2 = new Message(Severity.REGULAR, "Ciao mondo!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages From HashMap When Severity Is Null")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenSeverityIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(null, "Ciao mondo!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages From HashMap When Whole Message Is Null")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenWholeMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = null;
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages From HashMap When Whole Message Is Empty")
        public void ShouldNotDecorateSaveAndReturnMessageByKeyFromHashMapWhenWholeMessageIsEmpty() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "");
            Message message2 = new Message(Severity.REGULAR, "Ciao Mondo!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
    }

    @Nested
    @DisplayName("Process Ordered Messages Method")
    class ProcessOrderedMessages {

        @Test
        @DisplayName("Should Decorate Save And Return All Messages In ASC Order From HashMap When Is Valid")
        public void ShouldDecorateSaveAndReturnAllMessagesInASCOrderFromHashMapWhenIsValid() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.REGULAR, "Ciao mondo!");
            //

            //When
            sut.processMessages(MessageOrder.ASC, message1, message2);
            allMessages = sut.findAll();
            for (DecoratedMessage currentProcessedMessage : allMessages) {
                currentMessage = currentProcessedMessage;
                System.out.println(currentMessage);
            }
            //

            //Then
            assertThat(allMessages).isNotNull()
                    .extracting("level", "message", "id")
                    .contains(tuple(currentMessage.getLevel(), currentMessage.getMessage(), currentMessage.getId()))
                    .doesNotContainNull()
                    .hasSize(2);
            //
        }
        @Test
        @DisplayName("Should Decorate Save And Return All Messages In DESC Order From HashMap When Is Valid")
        public void ShouldDecorateSaveAndReturnAllMessagesInDESCOrderFromHashMapWhenIsValid() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.REGULAR, "Ciao mondo!");
            //

            //When
            sut.processMessages(MessageOrder.DESC, message1, message2);
            allMessages = sut.findAll();
            for (DecoratedMessage currentProcessedMessage : allMessages) {
                currentMessage = currentProcessedMessage;
                System.out.println(currentMessage);
            }
            //

            //Then
            assertThat(allMessages).isNotNull()
                    .extracting("level", "message", "id")
                    .contains(tuple(currentMessage.getLevel(), currentMessage.getMessage(), currentMessage.getId()))
                    .doesNotContainNull()
                    .hasSize(2);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In DESC Order From HashMap When Message Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInDESCOrderFromHashMapWhenMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.REGULAR, null);
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(MessageOrder.DESC, message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In DESC Order From HashMap When Severity Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInDESCOrderFromHashMapWhenSeverityIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(null, "Ciao Mondo!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(MessageOrder.DESC, message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In DESC Order From HashMap When Whole Message Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInDESCOrderFromHashMapWhenWholeMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = null;
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(MessageOrder.DESC, message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In DESC Order From HashMap When MessageOrder Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInDESCOrderFromHashMapWhenMessageOrderIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages((MessageOrder) null, message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In DESC Order From HashMap When Message Is Empty")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInDESCOrderFromHashMapWhenMessageIsEmpty() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, "");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages((MessageOrder) null, message1, message2);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);
            //
        }
    }

    @Nested
    @DisplayName("Process Doubling Ordered Messages Method")
    class ProcessDoublingOrderedMessages {
        @Test
        @DisplayName("Should Decorate Save And Return All Messages In ASC Order With Doubles From HashMap When Is Valid")
        public void ShouldDecorateSaveAndReturnAllMessagesInASCOrderWithDoublesFromHashMapWhenIsValid() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, "Hello world!");
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            sut.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
            allMessages = sut.findAll();
            for (DecoratedMessage currentProcessedMessage : allMessages) {
                currentMessage = currentProcessedMessage;
                System.out.println(currentMessage);
            }
            //

            //Then
            assertThat(allMessages).isNotNull()
                    .extracting("level", "message", "id")
                    .contains(tuple(currentMessage.getLevel(), currentMessage.getMessage(), currentMessage.getId()))
                    .doesNotContainNull()
                    .hasSize(6);
            //
        }
        @Test
        @DisplayName("Should Decorate Save And Return All Messages In ASC Order Distinct From HashMap When Is Valid")
        public void ShouldDecorateSaveAndReturnAllMessagesInASCOrderDistinctFromHashMapWhenIsValid() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, "Hello world!");
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            sut.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
            allMessages = sut.findAll();
            for (DecoratedMessage currentProcessedMessage : allMessages) {
                currentMessage = currentProcessedMessage;
                System.out.println(currentMessage);
            }
            //

            //Then
            assertThat(allMessages).isNotNull()
                    .doesNotHaveDuplicates()
                    .doesNotContainNull()
                    .extracting("level", "message", "id")
                    .contains(tuple(currentMessage.getLevel(), currentMessage.getMessage(), currentMessage.getId()))                    ;
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In ASC Order Doubles From HashMap When Message Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInASCOrderDoublesFromHashMapWhenMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, null);
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);                   ;
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In ASC Order Distinct From HashMap When Severity Is Null")
        public void ShouldDecorateSaveAndReturnAllMessagesInASCOrderDistinctFromHashMapWhenMessageIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, null);
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            sut.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
            allMessages = sut.findAll();
            for (DecoratedMessage currentProcessedMessage : allMessages) {
                currentMessage = currentProcessedMessage;
                System.out.println(currentMessage);
            }
            //

            //Then
            assertThat(allMessages).isNotNull()
                    .doesNotHaveDuplicates()
                    .doesNotContainNull()
                    .extracting("level", "message", "id")
                    .contains(tuple(currentMessage.getLevel(), currentMessage.getMessage(), currentMessage.getId()))                    ;
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In ASC Order Doubles From HashMap When Severity Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInASCOrderDoublesFromHashMapWhenSeverityIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(null, "Hello world!");
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);                   ;
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In ASC Order Doubles From HashMap When Order Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInASCOrderDoublesFromHashMapWhenOrderIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, "Hello world!");
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(null, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);                   ;
            //
        }
        @Test
        @DisplayName("Should Not Decorate Save And Return All Messages In ASC Order Doubles From HashMap When Doubling Is Null")
        public void ShouldNotDecorateSaveAndReturnAllMessagesInASCOrderDoublesFromHashMapWhenDoublingIsNull() throws LogException {
            //Given
            Message message1 = new Message(Severity.MAJOR, "Hello world!");
            Message message2 = new Message(Severity.MAJOR, "Hello world!");
            Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
            Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
            Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
            Message message6 = new Message(Severity.MAJOR, "Hello world!");
            //

            //When
            Throwable thrown = catchThrowable(() -> {
                sut.processMessages(MessageOrder.ASC, (Doubling) null, message1, message2, message3, message4, message5, message6);
            });
            //

            //Then
            assertThat(thrown)
                    .isInstanceOf(LogException.class);                   ;
            //
        }
    }
}
