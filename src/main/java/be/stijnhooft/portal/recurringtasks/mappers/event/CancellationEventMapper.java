package be.stijnhooft.portal.recurringtasks.mappers.event;

import be.stijnhooft.portal.model.domain.Event;
import be.stijnhooft.portal.recurringtasks.dtos.RecurringTaskDto;
import be.stijnhooft.portal.recurringtasks.mappers.Mapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CancellationEventMapper extends Mapper<RecurringTaskDto, Event> {

    private final String deploymentName;

    public CancellationEventMapper(@Value("${deployment-name}") String deploymentName) {
        this.deploymentName = deploymentName;
    }

    @Override
    public Event map(@NonNull RecurringTaskDto recurringTask) {
        Map<String, String> data = new HashMap<>();
        data.put("type", "cancellation");
        return new Event(deploymentName, deploymentName + "-" + recurringTask.getId(), LocalDateTime.now(), data);
    }
}
