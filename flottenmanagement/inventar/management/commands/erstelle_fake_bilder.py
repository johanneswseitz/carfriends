import openai
import requests
from django.core.files.base import ContentFile
from django.core.files.temp import NamedTemporaryFile
from inventar.models import Fahrzeug
from django.core.management.base import BaseCommand


def generate_and_save_assets_for_car(car: Fahrzeug):
    # 1. Realistisches Fahrzeugbild generieren
    image_response = openai.Image.create(
        prompt=f"Realistic photo of a {car.name}, parked on a street with german number plate {car.kennzeichen}.",
        n=1,
        size="1024x1024"
    )
    image_url = image_response['data'][0]['url']
    img_temp = NamedTemporaryFile(delete=True)
    img_temp.write(requests.get(image_url).content)
    img_temp.flush()
    car.vehicle_image.save(f"{car.name.lower().replace(' ', '_')}_image.png", ContentFile(img_temp.read()), save=False)

    # 2. Fahrzeugschein als stilisiertes Bild generieren
    doc_prompt = f"A realistic German vehicle registration document (Fahrzeugschein) for a {car.name}, filled with example data, document style, high-res scan"
    doc_response = openai.Image.create(
        prompt=doc_prompt,
        n=1,
        size="1024x1448"
    )
    doc_url = doc_response['data'][0]['url']
    doc_temp = NamedTemporaryFile(delete=True)
    doc_temp.write(requests.get(doc_url).content)
    doc_temp.flush()
    car.registration_document.save(f"{car.name.lower().replace(' ', '_')}_fahrzeugschein.png", ContentFile(doc_temp.read()), save=True)

class Command(BaseCommand):
    help = "Erstellt für alle Fahrzeuge in der Datenbank Fake-Bilder und Fahrzeugscheine über OpenAI (Dall-E)"

    def handle(self, *args, **kwargs):
        auto = Fahrzeug.objects.get(id=1)
        generate_and_save_assets_for_car(auto)




