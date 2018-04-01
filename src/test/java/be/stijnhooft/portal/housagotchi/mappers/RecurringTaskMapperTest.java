package be.stijnhooft.portal.housagotchi.mappers;

import be.stijnhooft.portal.housagotchi.dtos.RecurringTaskDTO;
import be.stijnhooft.portal.housagotchi.model.RecurringTask;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RecurringTaskMapperTest {

    private RecurringTaskMapper mapper;

    @Before
    public void setUp() {
        this.mapper = new RecurringTaskMapper();
    }

    @Test
    public void map() {
        RecurringTaskDTO recurringTaskDTO = new RecurringTaskDTO(1L, "test", 1, 3, LocalDate.now());

        RecurringTask recurringTask = mapper.map(recurringTaskDTO);

        assertNull(recurringTask.getId());
        assertEquals("test", recurringTask.getName());
        assertEquals(1, recurringTask.getMinNumberOfDaysBetweenExecutions());
        assertEquals(3, recurringTask.getMaxNumberOfDaysBetweenExecutions());
    }

    @Test(expected = NullPointerException.class)
    public void mapWhenPassingNull() {
        mapper.map((RecurringTaskDTO) null);
    }

}