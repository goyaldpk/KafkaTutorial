import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class MetricRegistryService {

    MetricRegistry registry = new MetricRegistry();

    final Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
    final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
            .prefixedWith("stats.gauges.metrics.Twitter")
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .filter(MetricFilter.ALL)
            .build(graphite);

    public MetricRegistryService(){
        reporter.start(1, TimeUnit.SECONDS);
    }

    public MetricRegistry getRegistry() {
        return registry;
    }
}
