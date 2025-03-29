package com.coronel.persistenceService.consumer;

import com.coronel.persistenceService.model.Participant;
import com.coronel.persistenceService.model.Result;
import com.coronel.persistenceService.model.Type;
import com.coronel.persistenceService.model.WINDRAWLOST;
import com.coronel.persistenceService.repository.ParticipantRepository;
import com.coronel.persistenceService.repository.ResultRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.ff4j.FF4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class KafkaConsumerServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ResultRepository resultRepository;

    @Spy
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Mock
    private FF4j ff4j;

    String name = "Iga Świątek";
    String country = "Poland";
    String urlIcon = "http://icon.com";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(kafkaConsumerService, "logger", logger);
    }

    @ParameterizedTest
    @EnumSource(value = Type.class, names = {"PERSON", "TEAM"})
    void addParticipant(Type type) throws JsonProcessingException {
        //given
        ObjectId id = new ObjectId();
        String message = """
                {
                    "name": "%s",
                    "country": "%s",
                    "type": "%s",
                    "urlIcon": "%s"
                }""".formatted(name, country, type, urlIcon);
        Participant participant = Participant.builder()
                .id(id)
                .name(name)
                .country(country)
                .type(type)
                .urlIcon(urlIcon)
                .build();
        when(participantRepository.save(any())).thenReturn(participant);
        when(ff4j.check(any())).thenReturn(true);

        //when
        kafkaConsumerService.addParticipant(message);

        //then
        //TODO: Improve the verify adding the values of the participant
        verify(participantRepository, times(1)).save(any());
        verify(logger, times(1)).info("**** Participant with name: {} saved with id: {} ****", participant.getName(), participant.getId());

    }

    @ParameterizedTest
    @EnumSource(value = Type.class, names = {"PERSON", "TEAM"})
    void deleteParticipant(Type type) throws JsonProcessingException {
        //given
        ObjectId id = new ObjectId();
        String message = """
                {"id": "%s"}
                """.formatted(id.toString());
        when(participantRepository.findById(id.toString())).thenReturn(java.util.Optional.of(new Participant(id, name, country, type, urlIcon)));
        when(ff4j.check(any())).thenReturn(true);

        //when
        kafkaConsumerService.deleteParticipant(message);

        //then
        verify(participantRepository, times(1)).deleteById(id.toString());
        verify(logger, times(1)).info("**** Participant deleted with name: {} and id: {} ****", name, id);
    }

    @Test
    void addResult() throws JsonProcessingException {
        //given
        ObjectId uui = new ObjectId();
        ObjectId participantId1 = new ObjectId();
        ObjectId participantId2 = new ObjectId();
        WINDRAWLOST winDrawLostParticipant1 = WINDRAWLOST.WIN;
        WINDRAWLOST winDrawLostParticipant2 = WINDRAWLOST.LOST;
        String result = "6-4, 7-5";
        String championship = "Australian Open";
        LocalDate date = LocalDate.of(2024, 10, 7);
        String message = """
                {
                "participantId1":"%s",
                "participantId2":"%s",
                "winDrawLostParticipant1":"%s",
                "winDrawLostParticipant2":"%s",
                "result":"%s",
                "date":"%s",
                "championship":"%s"
                }
                """.formatted(participantId1.toString(), participantId2.toString(), winDrawLostParticipant1, winDrawLostParticipant2, result, date.format(getDateTimeFormatter()), championship);
        Result resultObject = Result.builder()
                .id(uui)
                .participantId1(participantId1)
                .participantId2(participantId2)
                .winDrawLostParticipant1(winDrawLostParticipant1)
                .winDrawLostParticipant2(winDrawLostParticipant2)
                .result(result)
                .date(date)
                .championship(championship)
                .build();
        when(resultRepository.save(any())).thenReturn(resultObject);
        when(ff4j.check(any())).thenReturn(true);

        //when
        kafkaConsumerService.addResult(message);

        //then
        //TODO: Improve the verify adding the values of the result
        verify(resultRepository, times(1)).save(any());
        verify(logger, times(1)).info("**** Result saved with id: {} ****", resultObject.getId());
    }

    @Test
    void deleteResult() throws JsonProcessingException {
        //given
        ObjectId id = new ObjectId();
        String message = """
                {"id": "%s"}
                """.formatted(id.toString());
        Result result = Result.builder()
                .id(id)
                .participantId1(new ObjectId())
                .participantId2(new ObjectId())
                .winDrawLostParticipant1(WINDRAWLOST.LOST)
                .winDrawLostParticipant2(WINDRAWLOST.WIN)
                .result("7-5, 7-5")
                .championship("AO")
                .date(LocalDate.of(2024, 10, 7))
                .build();

        when(resultRepository.findById(id.toString())).thenReturn(Optional.ofNullable(result));
        when(ff4j.check(any())).thenReturn(true);

        //when
        kafkaConsumerService.deleteResult(message);

        //then
        verify(resultRepository, times(1)).deleteById(id.toString());
        verify(logger, times(1)).info("**** Result deleted with id: {} ****", id);
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("ddMMyyyy");
    }
}