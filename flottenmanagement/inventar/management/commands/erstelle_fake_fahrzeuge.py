from django.core.management.base import BaseCommand
from inventar.models import Fahrzeug
from faker import Faker
import random
from datetime import date, timedelta

class Command(BaseCommand):
    help = "Erstellt 30 Beispiel-Fahrzeuge mit realistischen Werten"

    def handle(self, *args, **kwargs):
        fake = Faker('de_DE')
        hersteller_modelle = {
            'Volkswagen': ['Golf', 'Passat', 'Tiguan'],
            'BMW': ['3er', '5er', 'X1'],
            'Mercedes': ['A-Klasse', 'C-Klasse', 'E-Klasse'],
            'Audi': ['A3', 'A4', 'Q5'],
            'Opel': ['Corsa', 'Astra', 'Insignia'],
            'Renault': ['Clio', 'Megane', 'Captur'],
        }

        farben = ['Schwarz', 'Weiß', 'Silber', 'Blau', 'Rot', 'Grau']
        kraftstoffe = ['B', 'D', 'E', 'H']
        getriebe = ['M', 'A']
        statuswahl = ['verfügbar', 'vermietet', 'wartung']

        for _ in range(30):
            hersteller = random.choice(list(hersteller_modelle.keys()))
            versicherer = fake.company()
            modell = random.choice(hersteller_modelle[hersteller])

            fahrzeug = Fahrzeug(
                kennzeichen=fake.license_plate().replace(" ", "").upper()[:8],
                hersteller=hersteller,
                modell=modell,
                baujahr=random.randint(2016, 2023),
                fahrgestellnummer=fake.unique.bothify(text='???########????###'),
                kilometerstand=random.randint(5000, 150000),
                kraftstoffart=random.choice(kraftstoffe),
                getriebeart=random.choice(getriebe),
                farbe=random.choice(farben),
                sitzanzahl=random.choice([4, 5, 7]),
                tueranzahl=random.choice([3, 5]),
                erstzulassung=fake.date_between(start_date='-7y', end_date='-1y'),
                naechste_hauptuntersuchung=date.today() + timedelta(days=random.randint(100, 1000)),
                versicherer=versicherer,
                versicherungsnummer=fake.bothify(text='??-#######'),
                mietpreis_pro_tag=round(random.uniform(35.0, 120.0), 2),
                status=random.choice(statuswahl),
                bemerkungen=""
            )
            fahrzeug.save()

        self.stdout.write(self.style.SUCCESS('30 Fahrzeuge erfolgreich erstellt.'))
