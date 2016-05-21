from django import forms


class DocumentForm(forms.Form):
    abc = forms.CharField(widget=forms.Textarea())
