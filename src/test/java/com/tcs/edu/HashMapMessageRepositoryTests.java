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

@DisplayName("HashMap Message Repository Tests")
public class HashMapMessageRepositoryTests {
    HashMapMessageRepository sut;
    DecoratedMessage currentMessage;
    UUID uuid;
    Iterable<DecoratedMessage> allMessages;

    @BeforeEach
    public void SetUp() {
        sut = new HashMapMessageRepository();
//        currentMessage = null;
//        uuid = null;
//        allMessages = null;
    }

    @Nested
    @DisplayName("Create Method")
    class Create {

        @Test
        @DisplayName("Should Return Uuid Of Message In HashMap When It's Exist")
        public void shouldReturnUuidOfMessageInHashMapWhenItsExist() {
            //Given
            DecoratedMessage message1 = new DecoratedMessage(Severity.MAJOR, "Hello world!", null);
            //

            //When
            uuid = sut.create(message1);
//            System.out.println(uuid);
            //

            //Then
            assertThat(uuid).isNotNull();
            //
        }
    }

    @Nested
    @DisplayName("Find By Primary Key Method")
    class FindByPrimaryKey {
        @Test
        @DisplayName("Should Find Message By Key In HashMap When It's Exist")
        public void ShouldFindMessageByKeyInHashMapWhenItsExist() {
            //Given
            DecoratedMessage message1 = new DecoratedMessage(Severity.MAJOR, "Hello world!", null);
            //

            //When
            currentMessage = sut.findByPrimaryKey(sut.create(message1));
//            System.out.println(currentMessage);
            //

            //Then
            assertThat(currentMessage).isNotNull()
                    .extracting("level", "message", "id")
                    .contains(message1.getLevel(), message1.getMessage())
                    .doesNotContainNull();
            //
        }
    }

    @Nested
    @DisplayName("Find All Method")
    class FindAll {
        @Test
        @DisplayName("Should Find All Messages In HashMap When It's Exist")
        public void ShouldFindAllMessagesInHashMapWhenItsExist() {
            //Given
            DecoratedMessage message1 = new DecoratedMessage(Severity.MAJOR, "Hello world!", null);
            DecoratedMessage message2 = new DecoratedMessage(Severity.REGULAR, "Ciao mondo!", null);
            //

            //When
            allMessages = Arrays.asList(message1, message2);
            for (DecoratedMessage currentMessage : allMessages) {
                sut.create(currentMessage);
            }
            allMessages = sut.findAll();
//            for (DecoratedMessage currentMappedMessage : allMessages) {
//                currentMessage = currentMappedMessage;
//                System.out.println(currentMappedMessage);
//            }
            //

            //Then
            assertThatIterable(allMessages).isNotNull()
                    .contains(message1, message2)
                    .doesNotContainNull()
                    .hasSize(2);
            //
        }
    }

    @Nested
    @DisplayName("Find By Severity Method")
    class FindBySeverity {
        @Test
        @DisplayName("Should Find Message By Severity In HashMap When It's Exist")
        public void ShouldFindMessageBySeverityInHashMapWhenItsExist() {
            //Given
            DecoratedMessage message1 = new DecoratedMessage(Severity.MAJOR, "Hello world!", null);
            DecoratedMessage message2 = new DecoratedMessage(Severity.MAJOR, "Hello world!", null);
            DecoratedMessage message3 = new DecoratedMessage(Severity.REGULAR, "Ciao mondo!", null);
            DecoratedMessage message4 = new DecoratedMessage(Severity.MINOR, "Hallo Welt!", null);
            DecoratedMessage message5 = new DecoratedMessage(Severity.REGULAR, "¡Hola Mundo!", null);
            DecoratedMessage message6 = new DecoratedMessage(Severity.MAJOR, "Hello world!", null);
            Severity severityFindBy = Severity.MAJOR;
            //

            //When
            allMessages = Arrays.asList(message1, message2, message3, message4, message5, message6);
            for (DecoratedMessage currentMessage : allMessages) {
                sut.create(currentMessage);
            }
            allMessages = sut.findBySeverity(severityFindBy);
//            for (DecoratedMessage currentMappedMessage : allMessages) {
//                currentMessage = currentMappedMessage;
//                System.out.println(currentMappedMessage);
//            }
            //

            //Then
            assertThatIterable(allMessages).isNotNull()
                    .doesNotContainNull()
                    .hasSize(3)
                    .extracting("level")
                    .containsAll(Collections.singleton(severityFindBy));
            //
        }
    }
}

