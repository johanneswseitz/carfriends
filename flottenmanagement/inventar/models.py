from django.db import models

class Fahrzeug(models.Model):
    KRAFTSTOFFARTEN = [
        ('B', 'Benzin'),
        ('D', 'Diesel'),
        ('E', 'Elektro'),
        ('H', 'Hybrid'),
    ]

    GETRIEBEARTEN = [
        ('M', 'Manuell'),
        ('A', 'Automatik'),
    ]

    STATUSWAHL = [
        ('verfügbar', 'Verfügbar'),
        ('vermietet', 'Vermietet'),
        ('wartung', 'In Wartung'),
        ('nicht_verfügbar', 'Nicht verfügbar'),
    ]

    kennzeichen = models.CharField("Kennzeichen", max_length=15, unique=True)
    hersteller = models.CharField("Hersteller", max_length=50)
    modell = models.CharField("Modell", max_length=50)
    baujahr = models.PositiveIntegerField("Baujahr")
    fahrgestellnummer = models.CharField("Fahrgestellnummer (FIN)", max_length=17, unique=True)
    kilometerstand = models.PositiveIntegerField("Kilometerstand")
    kraftstoffart = models.CharField("Kraftstoffart", max_length=1, choices=KRAFTSTOFFARTEN)
    getriebeart = models.CharField("Getriebeart", max_length=1, choices=GETRIEBEARTEN)
    farbe = models.CharField("Farbe", max_length=30)
    sitzanzahl = models.PositiveSmallIntegerField("Anzahl Sitze")
    tueranzahl = models.PositiveSmallIntegerField("Anzahl Türen")
    erstzulassung = models.DateField("Datum der Erstzulassung")
    naechste_hauptuntersuchung = models.DateField("Nächste Hauptuntersuchung (TÜV)")
    versicherer = models.CharField("Versicherung", max_length=100, blank=True, null=True)
    versicherungsnummer = models.CharField("Versicherungsnummer", max_length=50, blank=True, null=True)
    mietpreis_pro_tag = models.DecimalField("Mietpreis pro Tag (€)", max_digits=7, decimal_places=2)
    status = models.CharField("Status", max_length=20, choices=STATUSWAHL, default='verfügbar')
    bemerkungen = models.TextField("Bemerkungen", blank=True, null=True)
    foto = models.ImageField(upload_to='fahrzeug-fotos/', null=True, blank=True)
    fahrzeugschein = models.FileField(upload_to="fahrzeugscheine/", null=True, blank=True)

    @property
    def name(self):
        return f"{self.hersteller} {self.modell}"

    class Meta:
        verbose_name = "Fahrzeug"
        verbose_name_plural = "Fahrzeuge"
        ordering = ['kennzeichen']

    def __str__(self):
        return f"{self.hersteller} {self.modell} ({self.kennzeichen})"
