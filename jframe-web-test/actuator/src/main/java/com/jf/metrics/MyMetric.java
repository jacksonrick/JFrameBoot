package com.jf.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

import static java.util.Collections.emptyList;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义指标
 * User: xujunfei
 * Date: 2019-08-13
 * Time: 11:58
 */
public class MyMetric implements MeterBinder {

    private static Logger log = LoggerFactory.getLogger(MyMetric.class);

    private final Iterable<Tag> tags;
    private AtomicLong data = new AtomicLong(1);

    public MyMetric() {
        this(emptyList());
    }

    public MyMetric(Iterable<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        for (int i = 1; i <= 2; i++) {
            Iterable<Tag> tagsWithId = Tags.concat(tags, "id", "key" + i);
            Gauge.builder("custom.data", data, AtomicLong::get)
                    .tags(tagsWithId)
                    .description("my custom data")
                    .register(meterRegistry);
        }
    }
}
