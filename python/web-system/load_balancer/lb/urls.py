"""Defines URL patterns for lb."""

from django.urls import path

from . import views

app_name = 'lb'
urlpatterns = [
    path('', views.forward_request, name='forward_request'),
    path('many', views.simulate_many_requests, name='simulate_many_requests'),
    path('increment_requests', views.increment_requests, name='increment_requests'),
]