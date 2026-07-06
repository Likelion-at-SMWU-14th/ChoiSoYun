from django.urls import path
from rest_framework.authtoken.views import obtain_auth_token

from .views import SignupView


app_name = 'accounts'

urlpatterns = [
    path('signup/', SignupView.as_view()),
    path('login/', obtain_auth_token),
]