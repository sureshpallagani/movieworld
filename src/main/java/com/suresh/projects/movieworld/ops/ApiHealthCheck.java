package com.suresh.projects.movieworld.ops;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

@Component
public class ApiHealthCheck implements HealthIndicator {
	
	@Autowired private MetricRegistry metricRegistry;

	@Override
	public Health health() {
		if (isHealthy()) {
			return Health.up().build();
		}
		return Health.outOfService().build();
	}

	/**
	 * Should be reported as unhealthy if any 5xx series errors.
	 * @return
	 */
	private boolean isHealthy() {
		final Map<String, Counter> allCounters = metricRegistry.getCounters();
		return allCounters.keySet().parallelStream().filter(s -> s.contains("counter.status.5")).count() == 0;
	}

}
