from django.contrib import admin
from django.utils.html import format_html

from inventar.models import Fahrzeug

@admin.register(Fahrzeug)
class FahrzeugAdmin(admin.ModelAdmin):
    def image_tag(self, obj):
        if obj.foto:
            return format_html('<img src="{}" style="max-width: 100px;max-height:100px"/>'.format(obj.foto.url))
        else:
            return ""

    image_tag.short_description = 'Image'

    list_display = ['image_tag', '__str__',]
    readonly_fields = ('image_tag',)
    pass

