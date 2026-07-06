from django.urls import include, path
from rest_framework import routers

from .views import (
    PostListView,
    PostRetrieveView,
    PostModelViewSet,
)


app_name = 'posts'

router_post = routers.DefaultRouter()
router_post.register('', PostModelViewSet)


urlpatterns = [
    # Generic Views
    path('generic/', PostListView.as_view()),
    path('generic/<int:pk>/', PostRetrieveView.as_view()),

    # ViewSet + Router
    path('', include(router_post.urls)),
]