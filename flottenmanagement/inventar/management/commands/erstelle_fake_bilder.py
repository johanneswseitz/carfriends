from openai import OpenAI, BadRequestError

client = OpenAI()
import requests
from django.core.files.base import ContentFile
from django.core.files.temp import NamedTemporaryFile
from inventar.models import Fahrzeug
from django.core.management.base import BaseCommand


def generate_and_save_assets_for_car(car: Fahrzeug):

    try:
        response = (client.images.generate
            #

            (
            model="dall-e-3",
            prompt="A futuristic cityscape at sunset",
            size="1024x1024",
            n=1
        ))
    except BadRequestError as e:
        print(f"BadRequestError: {e}")
        if hasattr(e, 'response') and e.response:
            print(f"Response content: {e.response.text}")
        raise

    # 1. Realistisches Fahrzeugbild generieren
    try:
        image_response = client.images.generate(
            model="dall-e-3",
            prompt=f"Realistic photo of a {car.name}, parked on a street.",
            size="1024x1024",
        )
    except BadRequestError as e:
        print(f"BadRequestError: {e}")
        raise
    image_url = image_response.data[0].url
    img_temp = NamedTemporaryFile(delete=True)
    img_temp.write(requests.get(image_url).content)
    img_temp.flush()
    car.foto.save(f"{car.name.lower().replace(' ', '_')}_image.png", ContentFile(img_temp.read()), save=False)

    # 2. Fahrzeugschein als stilisiertes Bild generieren
    #doc_prompt = f"A realistic German vehicle registration document (Fahrzeugschein) for a {car.name}, filled with example data, document style, high-res scan"
    #doc_response = client.images.generate(prompt=doc_prompt,
    #                                      model="dall-e-3",
    #                                      size="1024x1024")
    #doc_url = doc_response.data[0].url
    #doc_temp = NamedTemporaryFile(delete=True)
    #doc_temp.write(requests.get(doc_url).content)
    #doc_temp.flush()
    #car.fahrzeugschein.save(f"{car.name.lower().replace(' ', '_')}_fahrzeugschein.png", ContentFile(doc_temp.read()), save=True)


class Command(BaseCommand):
    help = "Erstellt für alle Fahrzeuge in der Datenbank Fake-Bilder und Fahrzeugscheine über OpenAI (Dall-E)"

    def handle(self, *args, **kwargs):
        auto = Fahrzeug.objects.get(id=1)
        print(auto)
        generate_and_save_assets_for_car(auto)




