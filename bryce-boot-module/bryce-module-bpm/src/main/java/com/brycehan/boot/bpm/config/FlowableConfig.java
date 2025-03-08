package com.brycehan.boot.bpm.config;

import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateInoker;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmUserApi;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Bryce Han
 * @since 2025/3/4
 */
@Configuration
public class FlowableConfig {

    public BpmTaskCandidateInoker bpmTaskCandidateInoker(List<BpmTaskCandidateStrategy> bpmTaskCandidateStrategies, BpmUserApi bpmUserApi) {
        return new BpmTaskCandidateInoker(bpmTaskCandidateStrategies, bpmUserApi);
    }
}
