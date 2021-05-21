from django.http import HttpResponse
import json
import requests
import time
from .work_distributer import WorkDistributer

def increment_requests(request):
    print("Received Request")
    response = requests.get('http://127.0.0.1:8020/increment_requests', params=request.GET)
    payload = json.loads(response.content)
    time.sleep(0.5)
    create_work_item(payload['count'])
    return create_django_response(response)

def create_django_response(response):
    django_response = HttpResponse(
        content=response.content,
        status=response.status_code,
        content_type=response.headers['Content-Type']
    )

    return django_response

def create_work_item(key):
    WorkDistributer().submit_work_item(str(key), f"Work Item {key}")