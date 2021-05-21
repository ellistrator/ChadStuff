from kafka import KafkaProducer

class Producer:
    def __init__(self, topic_name):
        self.producer = self.connect()
        self.topic_name = topic_name

    def connect(self):
        producer = None
        try:
            producer = KafkaProducer(bootstrap_servers=['localhost:9092'], api_version=(0, 10))
        except Exception as ex:
            print('Exception while connecting to Kafka')
            print(str(ex))
        finally:
            return producer

    def publish_message(self, key, value):
        try:
            key_bytes = bytes(key, encoding='utf-8')
            value_bytes = bytes(value, encoding='utf-8')
            self.producer.send(self.topic_name, key=key_bytes, value=value_bytes)
            self.producer.flush()
        except Exception as ex:
            print('Exception in publishing message')
            print(str(ex))