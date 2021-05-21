import kafka.producer
from .kafka.producer import Producer

class WorkDistributer:
    def __init__(self):
        self.producer = Producer(topic_name = "work_items")

    def submit_work_item(self, key, value):
        self.producer.publish_message(key, value)
        print(f"Published {value}")