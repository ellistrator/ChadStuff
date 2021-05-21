from django.http import HttpResponse
import requests
from random import randrange
import concurrent.futures
import json

def forward_request(request):
    response = requests.get(f'http://127.0.0.1:800{randrange(10)}/increment_requests', params=request.GET)
    return create_django_response(response)

def increment_requests(request):
    response = requests.get(f'http://127.0.0.1:800{randrange(10)}/increment_requests', params=request.GET)
    return create_django_response(response)

def simulate_many_requests(request):
    times = randrange(200)
    print(f"Firing {times} requests")
    response = None
    with concurrent.futures.ThreadPoolExecutor() as executor:
        futures = []
        for i in range(0, times):
            futures.append(executor.submit(send_one_request, request))
        maxx = 0
        for future in concurrent.futures.as_completed(futures):
            payload = json.loads(future.result().content)
            if payload['count'] > maxx:
                maxx = payload['count']
                response = future.result()

    return create_django_response(response)

def send_one_request(request):
    rand = randrange(10)
    return requests.get(f'http://127.0.0.1:800{rand}/increment_requests', params=request.GET)

def create_django_response(response):
    django_response = HttpResponse(
        content=response.content,
        status=response.status_code,
        content_type=response.headers['Content-Type']
    )

    return django_response
