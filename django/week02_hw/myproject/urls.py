from django.contrib import admin
from django.urls import path
from posts.views import mood_fbv, MoodDiaryCBV

urlpatterns = [
    path('admin/', admin.site.urls),

    path('mood/diary/', MoodDiaryCBV.as_view()),
    path('mood/<str:mood>/', mood_fbv),
]