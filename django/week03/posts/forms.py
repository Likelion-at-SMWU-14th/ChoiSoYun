from django import forms

class PostBasedForm(forms.Form):
    image = forms.ImageField()
    content = forms.CharField(min_length=5)