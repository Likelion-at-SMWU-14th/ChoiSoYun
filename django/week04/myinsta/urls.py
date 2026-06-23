from django.contrib import admin
from django.urls import path, include

from django.conf import settings
from django.conf.urls.static import static

from posts.views_cal import calculator_query, calculator_body


urlpatterns = [
    path('admin/', admin.site.urls),
    path('posts/', include('posts.urls', namespace='posts')),
    path('calculate/query/', calculator_query),
    path('calculate/body/', calculator_body),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)