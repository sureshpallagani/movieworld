package com.suresh.projects.movieworld.ops;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

/**
 * Exports metrics data to any external system (TODO - Redis solution) every 1 minute, for further log analysis (TODO - ELK or any other). 
 */
@Component
public class ApiMetricsExporter {
	
	@Autowired private MetricRegistry metricRegistry;

	@Scheduled(fixedRate = 1000*60)
	public void exportMetrics() {
		final Map<String, Counter> allCounters = metricRegistry.getCounters();
		final Map<String, Gauge> allGauges = metricRegistry.getGauges();
		//We can log the above data and configure more if required - TODO Redis
	}
	
}
