package be.stijnhooft.portal.housagotchi.mappers.event;

import be.stijnhooft.portal.housagotchi.PortalHousagotchi;
import be.stijnhooft.portal.housagotchi.dtos.RecurringTaskDTO;
import be.stijnhooft.portal.model.domain.Event;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Map;

import static be.stijnhooft.portal.housagotchi.PortalHousagotchi.APPLICATION_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReminderEventMapperTest {

    private ReminderEventMapper eventMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init() {
        eventMapper = new ReminderEventMapper();
    }

    @Test
    public void mapReminderWhenNull() {
        expectedException.expect(NullPointerException.class);
        eventMapper.map((RecurringTaskDTO) null);
    }

    @Test
    public void mapReminderWhenNotUrgentTask() {
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        RecurringTaskDTO recurringTaskDTO = new RecurringTaskDTO(
                1L, "Do the dishes", 2,
                3, twoDaysAgo);

        Event event = eventMapper.map(recurringTaskDTO);

        assertEquals(PortalHousagotchi.APPLICATION_NAME, event.getSource());
        assertNotNull(event.getPublishDate());
        assertEquals(APPLICATION_NAME + "-1", event.getFlowId());

        Map<String, String> data = event.getData();
        assertEquals(Boolean.FALSE.toString(), data.get("urgent"));
        assertEquals("Do the dishes", data.get("task"));
        assertEquals(twoDaysAgo.toString(), data.get("lastExecution"));
        assertEquals("reminder", data.get("type"));
    }

    @Test
    public void mapReminderWhenUrgentTask() {
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        RecurringTaskDTO recurringTaskDTO = new RecurringTaskDTO(
                1L, "Do the dishes", 2,
                3, threeDaysAgo);

        Event event = eventMapper.map(recurringTaskDTO);

        assertEquals(PortalHousagotchi.APPLICATION_NAME, event.getSource());
        assertNotNull(event.getPublishDate());
        assertEquals(APPLICATION_NAME + "-1", event.getFlowId());

        Map<String, String> data = event.getData();
        assertEquals(Boolean.TRUE.toString(), data.get("urgent"));
        assertEquals("Do the dishes", data.get("task"));
        assertEquals(threeDaysAgo.toString(), data.get("lastExecution"));
        assertEquals("reminder", data.get("type"));
    }
}