from django.shortcuts import render
from django.http import HttpResponse
from django.views import View
from .models import MoodDiary

def mood_fbv(request, mood):
    return HttpResponse(f"오늘의 감정은 {mood}입니다.")

class MoodDiaryCBV(View):

    def get(self, request):
        mood_diary_list = MoodDiary.objects.all()

        context = {
            'mood_diary_list': mood_diary_list,
        }

        return render(request, 'mood_diary.html', context)