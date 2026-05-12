from django.db import models

class Story(models.Model):
    title = models.CharField(max_length=100, verbose_name='스토리 제목')
    image = models.ImageField(upload_to='stories/', verbose_name='이미지')
    created_at = models.DateTimeField(auto_now_add=True, verbose_name='작성일')
    likes = models.IntegerField(default=0, verbose_name='좋아요 수')

    def __str__(self):
        return self.title