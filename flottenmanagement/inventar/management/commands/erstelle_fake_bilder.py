from openai import OpenAI, BadRequestError
import base64

client = OpenAI()
import requests
from django.core.files.base import ContentFile
from django.core.files.temp import NamedTemporaryFile
from inventar.models import Fahrzeug
from django.core.management.base import BaseCommand


def generate_and_save_assets_for_car(car: Fahrzeug):
    try:
        prompt = f"Generate an image of {car.farbe} coloured {car.name} with a german license plate ({car.kennzeichen}) on a parking place that is marked 'Carfriends Car Sharing'"
        print(f"Car without image: {car}")
        print(f"Using Prompt: {prompt}")
        image_response = client.images.generate(
            model="gpt-image-1",
            prompt=prompt,
            quality="medium",
            size="1024x1024",
            n=1,
        )
        b64_image = image_response.data[0].b64_json
        image_data = base64.b64decode(b64_image)

        # Save to car.foto
        img_temp = NamedTemporaryFile(delete=False)
        img_temp.write(image_data)
        img_temp.flush()
        img_temp.seek(0)
        car.foto.save(f"{car.name.lower().replace(' ', '_')}_image.png", ContentFile(img_temp.read()), save=True)
    except BadRequestError as e:
        print(f"BadRequestError: {e}")
        raise

class Command(BaseCommand):
    help = "Erstellt für alle Fahrzeuge ohne Bild in der Datenbank Fake-Bilder über OpenAI (Dall-E)"

    def handle(self, *args, **kwargs):
        autos = Fahrzeug.objects.filter(foto='')
        for auto in autos:
            generate_and_save_assets_for_car(auto)




