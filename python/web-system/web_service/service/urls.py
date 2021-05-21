"""Defines URL patterns for web service."""

from django.urls import path

from . import views

app_name = 'service'
urlpatterns = [
    path('increment_requests', views.increment_requests, name='increment_requests'),
]