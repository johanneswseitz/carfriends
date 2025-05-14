from django.http import HttpResponse
from django.shortcuts import render

from inventar.models import Fahrzeug


def index(request):
    fahrzeuge = Fahrzeug.objects.all()
    return render(request, "inventar/index.html", locals())
