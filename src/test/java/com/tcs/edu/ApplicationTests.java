package com.tcs.edu;

import com.tcs.edu.decorator.OrderDoublingProcessor;
import com.tcs.edu.decorator.PagingDecorator;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.*;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.service.DecoratingMessageService;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;

public class ApplicationTests {

    private MessageService sut = null;

    @BeforeAll
    public static void globalSetUp() {

    }

    @BeforeEach
    public void setUp() {
        sut = new DecoratingMessageService(
                new HashMapMessageRepository(),
                new OrderDoublingProcessor(),
                new TimestampMessageDecorator(),
                new PagingDecorator());
    }

    @AfterEach
    public void tearDown() {

    }

    @AfterAll
    public static void globalTearDown() {

    }

//    @Test
//    public void shouldPrintStringWithDecoratedMessage() {
//        service.processMessages(message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
//        service.processMessages(MessageOrder.DESC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
//
//        System.out.println(message1);
//        System.out.println(message1.equals(message2));
//        System.out.println(message1.equals(message3));
//        System.out.println(message1.hashCode()==message2.hashCode());
//        System.out.println(message1.hashCode()==message3.hashCode());
//        service.processMessage(message3);
//    }

    @Test
    public void shouldPrintElementWhenItsValid() {
        //region Given | Arrange
        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        DecoratedMessage message;
        //endregion

        //region When | Act
        message = sut.findByPrimaryKey(sut.processMessage(message1));
        System.out.println(message);
        //endregion

        //region Then | Assert
        assertThat(message).extracting("level", "message", "id")
                .isNotNull();
        //endregion
    }

    @Test
    public void shouldPrintElementsWhenItsValid() {
        //region Given | Arrange
        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        Message message2 = new Message(Severity.MAJOR, "Hello world!");
        Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
        Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
        Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");
        //endregion

        //region When | Act
        sut.processMessages(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        final Collection<DecoratedMessage> allMessages = sut.findAll();
        for (DecoratedMessage current : allMessages) {
            System.out.println(current);
        }
        //endregion

        //region Then | Assert
        assertThat(allMessages).extracting("level", "message", "id")
                .isNotNull();
        //endregion
    }

    @Test
    public void shouldPrintFilteredElementsWhenItsValid() {
        //region Given | Arrange
        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        Message message2 = new Message(Severity.MAJOR, "Hello world!");
        Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
        Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
        Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");
        Severity severityFindBy = Severity.MAJOR;
        //endregion

        //region When | Act
        sut.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
        final Collection<DecoratedMessage> filteredMessages = sut.findBySeverity(severityFindBy);
        for (DecoratedMessage current : filteredMessages) {
            System.out.println(current);
        }
        //endregion

        //region Then | Assert
        assertThat(filteredMessages).extracting("level", "message", "id")
                .isNotNull();
        assertThat(filteredMessages).extracting("level")
                .isEqualTo(severityFindBy);
        //endregion
    }

    @Test
    public void shouldNotPrintElementWhenItsInvalid() {
        //region Given | Arrange
        Message message9 = new Message(Severity.MAJOR, null);
        DecoratedMessage message;
        //endregion

        //region When | Act
        message = sut.findByPrimaryKey(sut.processMessage(message9));
        System.out.println(message);
        //endregion

        //region Then | Assert
        assertThat(message).extracting("level", "message", "id")
                .isNotNull();
        //endregion
    }

    @Test
    public void shouldNotPrintElementsWhenOrderInvalid() {
        //region Given | Arrange
        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        Message message2 = new Message(Severity.MAJOR, "Hello world!");
        Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
        Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
        Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");
        //endregion

        //region When | Act
        sut.processMessages(null, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        final Collection<DecoratedMessage> allMessages = sut.findAll();
        for (DecoratedMessage current : allMessages) {
            System.out.println(current);
        }
        //endregion

        //region Then | Assert
        assertThat(allMessages).extracting("level", "message", "id")
                .isNotNull();
        //endregion
    }

    @Test
    public void shouldNotPrintElementsWhenDoublingInvalid() {
        //region Given | Arrange
        Message message1 = new Message(Severity.MAJOR, "Hello world!");
        Message message2 = new Message(Severity.MAJOR, "Hello world!");
        Message message3 = new Message(Severity.REGULAR, "Ciao mondo!");
        Message message4 = new Message(Severity.MINOR, "Hallo Welt!");
        Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");
        //endregion

        //region When | Act
        sut.processMessages(MessageOrder.ASC, (Doubling) null, message1, message2, message3, message4, message5, message6);
        final Collection<DecoratedMessage> allMessages = sut.findAll();
        for (DecoratedMessage current : allMessages) {
            System.out.println(current);
        }
        //endregion

        //region Then | Assert
        assertThat(allMessages).extracting("level", "message", "id")
                .isNotNull();
        //endregion
    }

    @Test
    public void shouldNotFilterElementsWhenSeverityInvalid() {
        //region Given | Arrange
        Message message5 = new Message(Severity.REGULAR, "¡Hola Mundo!");
        Message message6 = new Message(Severity.MAJOR, "Hello world!");
        Message message7 = new Message(Severity.MINOR, "");
        Message message8 = new Message(null, "Hello world!");
        Message message9 = new Message(Severity.MAJOR, null);
        Message message10 = new Message(Severity.MAJOR, "Hello world!");
        //endregion

        //region When | Act
        sut.processMessages(MessageOrder.ASC, Doubling.DOUBLES, message5, message6, message7, message8, message9, message10);
        final Collection<DecoratedMessage> filteredMessages = sut.findBySeverity(Severity.MAJOR);
        for (DecoratedMessage current : filteredMessages) {
            System.out.println(current);
        }
        //endregion

        //region Then | Assert
        assertThat(filteredMessages).extracting("level", "message", "id")
                .isNotNull();
        //endregion
    }
}
