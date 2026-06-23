from django.shortcuts import render
from rest_framwork.veiwsets import ModelViewSet

from .models import Post
from .serializers import PostModelSerializer

class PostModelViewSet(ModelViewSet):
    queryset = post.objects.all()
    serializer_class = PostModelSerializer
# Create your views here.
