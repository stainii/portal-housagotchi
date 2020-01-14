package be.stijnhooft.portal.recurringtasks.mappers.event;

import be.stijnhooft.portal.model.domain.Event;
import be.stijnhooft.portal.recurringtasks.dtos.RecurringTaskDto;
import be.stijnhooft.portal.recurringtasks.mappers.Mapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReminderEventMapper extends Mapper<RecurringTaskDto, Event> {

    private final String deploymentName;

    public ReminderEventMapper(@Value("${deployment-name}") String deploymentName) {
        this.deploymentName = deploymentName;
    }

    @Override
    public Event map(@NonNull RecurringTaskDto recurringTask) {
        LocalDate lastExecution = recurringTask.getLastExecution();
        LocalDate lastAcceptableDayOfExecution = LocalDate.now().minusDays(recurringTask.getMaxNumberOfDaysBetweenExecutions());

        Map<String, String> data = new HashMap<>();
        data.put("type", "reminder");
        data.put("urgent", Boolean.toString(lastExecution.isEqual(lastAcceptableDayOfExecution)));
        data.put("task", recurringTask.getName());
        data.put("lastExecution", recurringTask.getLastExecution().toString());

        return new Event(deploymentName, deploymentName + "-" + recurringTask.getId(), LocalDateTime.now(), data);
    }
}