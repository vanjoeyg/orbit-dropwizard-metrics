/*
 Copyright (C) 2016 Electronic Arts Inc.  All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1.  Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2.  Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3.  Neither the name of Electronic Arts, Inc. ("EA") nor the names of
     its contributors may be used to endorse or promote products derived
     from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY ELECTRONIC ARTS AND ITS CONTRIBUTORS "AS IS" AND ANY
 EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL ELECTRONIC ARTS OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package cloud.orbit.actors.extensions.metrics.dropwizard;

import com.codahale.metrics.MetricRegistry;

import cloud.orbit.actors.extensions.ActorExtension;
import cloud.orbit.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgong on 12/15/16.
 */
public class MetricsExtension implements ActorExtension {
    private List<ReporterConfig> metricsConfig = new ArrayList<>();

    /**
     * Alternative way to construct the extension, client is responsible to setup the
     * MetricRegistry
     */
    public MetricsExtension(MetricRegistry metricRegistry) {
        if (metricRegistry == null) {
            throw new IllegalArgumentException("metricRegistry cannot be null");
        }
        MetricsManager.getInstance().setRegistry(metricRegistry);
    }

    public MetricsExtension() {
        MetricsManager.getInstance().setRegistry(new MetricRegistry());
    }

    public MetricsExtension(List<ReporterConfig> metricsConfigs) {
        if (metricsConfigs == null) {
            throw new IllegalArgumentException("metricsConfigs cannot be null");
        }
        metricsConfig.addAll(metricsConfigs);
        MetricsManager.getInstance().setRegistry(new MetricRegistry());
    }

    @Override
    public Task<?> start() {
        MetricsManager.getInstance().initializeMetrics(metricsConfig);
        return Task.done();
    }

    @Override
    public Task<?> stop() {
        return Task.done();
    }

}
