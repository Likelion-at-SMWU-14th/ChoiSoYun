from django.urls import path

from .views import signup_api_view, user_detail_api_view


urlpatterns = [
    path('', signup_api_view, name='signup'),
    path('<int:user_id>/', user_detail_api_view, name='user-detail'),
]