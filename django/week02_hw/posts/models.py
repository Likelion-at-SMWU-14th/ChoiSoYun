from django.db import models

class MoodDiary(models.Model):
    mood = models.CharField(verbose_name="감정", max_length=50)
    content = models.TextField(verbose_name="일기 내용")
    created_at = models.DateTimeField(verbose_name="작성일", auto_now_add=True)

    def __str__(self):
        return self.mood