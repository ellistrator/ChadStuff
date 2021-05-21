"""Defines URL patterns for db."""

from django.urls import path

from . import views

app_name = 'db'
urlpatterns = [
    path('increment_requests', views.increment_requests, name='increment_requests'),
]