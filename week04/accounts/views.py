from django.shortcuts import render, redirect
from .forms import Signupform

def signup(request):
    if request.method == "POST":
        form = Signupform(request.POST)
        if form.is_valid():
            form.save()
            return redirect('login')
    else:
        form = Signupform()

    return render(request, 'signup.html', {'form': form})

# Create your views here.
