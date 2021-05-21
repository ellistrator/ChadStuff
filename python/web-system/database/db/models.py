from django.db import models

class RequestCount(models.Model):
    name = models.CharField(max_length=20, null=True)
    counter = models.BigIntegerField()

    def __str__(self):
        return f"{self.name}:{self.counter}"