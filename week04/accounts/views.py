from django.shortcuts import render, redirect
from .forms import Signupform
from django.contrib.auth import authenticate, login as auth_login

def signup(request):
    if request.method == "POST":
        form = Signupform(request.POST)
        if form.is_valid():
            form.save()
            return redirect('login')
    else:
        form = Signupform()

    return render(request, 'signup.html', {'form': form})

def login(request):
    if request.method == "POST":
        username = request.POST['username']
        password = request.POST['password']

        user = authenticate(request, username=username, password=password)
        if user is not None:
            auth_login(request, user)
            return redirect('main')
        
    return render(request, 'login.html')

def main(request):
    return render(request, "main.html")