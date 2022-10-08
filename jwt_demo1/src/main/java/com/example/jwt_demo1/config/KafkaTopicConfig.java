//package com.example.jwt_demo1.config;
//
//
//import org.apache.kafka.common.serialization.Serdes;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.EnableKafkaStreams;
//import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
//import org.springframework.kafka.config.KafkaStreamsConfiguration;
//
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//
//@Configuration
//@EnableKafka
//@EnableKafkaStreams
//public class KafkaTopicConfig {
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Value(value = "${spring.kafka.streams.state.dir}")
//    private String stateStoreLocation;
//
////    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
////    KafkaStreamsConfiguration kStreamsConfig() {
////        Properties props = new Properties();
////        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
////        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
////        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
////        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
////
////        StreamsBuilder builder = new StreamsBuilder();
////        builder.<String, String>stream("my-input-topic").mapValues(value -> String.valueOf(value.length())).to("my-output-topic");
////
////        KafkaStreams streams = new KafkaStreams(builder.build(), props);
////        streams.start();
////
////    }
//}
