import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class App {

    //Metrics configuration to send data to Graphite.
    private static final MetricRegistryService registryService = new MetricRegistryService();
    private static MetricRegistry registry = registryService.getRegistry();

    public static void main(String[] args) throws Exception {
        runConsumer();
    }

    static void runConsumer() {
        Consumer<String, JsonNode> consumer = ConsumerCreator.createConsumer();
        final int giveUp = 1000;   int noRecordsCount = 0;
        while (true) {
            final ConsumerRecords<String, JsonNode> consumerRecords =
                    consumer.poll(1000);
            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            consumerRecords.forEach(record -> {
                /*System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());*/
                //Doing analysis on data with simple text comparison.
                registry.counter("TotalUsers").inc();
                if(record.value().has("text")){
                    String text = record.value().get("text").asText();
                    if(text.contains("water")||text.contains("save")||text.contains("groundwater")||text.contains("conservation"))
                        registry.counter("WaterSentiments").inc();
                    if(text.contains("Entertainment")||text.contains("games")||text.contains("movies")||text.contains("film"))
                        registry.counter("Entertainment").inc();
                    if(text.contains("Sports")||text.contains("cricket")||text.contains("football")||text.contains("fitness"))
                        registry.counter("Sport").inc();
                    if(text.contains("COVID19")||text.contains("corona")||text.contains("Corona")||text.contains("lockdown"))
                        registry.counter("Corona").inc();
                }
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
}
