from django.http import HttpResponse
import requests
from random import randrange
from db.models import RequestCount

def increment_requests(request):
    print(RequestCount.objects.all())
    #RequestCount.objects.all().delete()

    try:
        rc = RequestCount.objects.get(name="thecounter")
    except:
        rc = RequestCount(counter = 0, name="thecounter")
    rc.counter += 1
    rc.save()
    print(f"Counter = {rc.counter}")

    return create_django_response(rc.counter)

def create_django_response(count):
    django_response = HttpResponse(
        content=f'{{"count":{count}}}',
        status=200,
        content_type='application/json'
    )

    return django_response
