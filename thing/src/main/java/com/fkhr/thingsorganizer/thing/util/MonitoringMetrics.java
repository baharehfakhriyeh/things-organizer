package com.fkhr.thingsorganizer.thing.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

@Component
public class MonitoringMetrics implements MeterBinder {
    private Counter thingsCreated;

    @Override
    public void bindTo(MeterRegistry registry) {
        System.out.println("MonitoringMetrics bindTo CALLED");
        this.thingsCreated = Counter.builder("thingsorganizer.thing.created")
                .tag("action", "created")
                .description("Number of things created.")
                .register(registry);
    }

    public void incrementThingsCreated(){
        this.thingsCreated.increment();
    }

}
