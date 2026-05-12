from django.contrib import admin
from django.urls import path
from django.conf import settings
from django.conf.urls.static import static
from posts.views import function_view, url_view, url_parameter_view

urlpatterns = [
    path('admin/', admin.site.urls),
    path('url/', url_view),
    path('url/<str:username>/', url_parameter_view),
    path('fbv/', function_view),
]

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)