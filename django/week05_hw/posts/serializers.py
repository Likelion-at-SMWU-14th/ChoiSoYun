from rest_framework import serializers

from .models import Post


class PostModelSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = [
            'id',
            'writer',
            'title',
            'content',
            'created_at',
        ]
        read_only_fields = [
            'writer',
            'created_at',
        ]