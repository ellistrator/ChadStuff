from kafka import KafkaConsumer

class Consumer:
    def __init__(self, topic_name):
        self.consumer = self.connect(topic_name)

    def connect(self, topic_name):
        consumer = None
        try:
            consumer = KafkaConsumer(topic_name, auto_offset_reset='latest',
                bootstrap_servers=['localhost:9092'], api_version=(0, 10))
        except Exception as ex:
            print('Exception while connecting to Kafka')
            print(str(ex))
        finally:
            return consumer

    def consume_messages(self):
        try:
            for message in self.consumer:
                print(f'Received {message.value}')
        except Exception as ex:
            print('Exception in consuming message')
            print(str(ex))

if __name__ == '__main__':
    # Create a Consumer and wait for messages
    c = Consumer('work_items')
    c.consume_messages()
