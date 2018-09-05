# Useful Links

**Mapping**

Mapping Worksheet:
https://docs.google.com/spreadsheets/d/1nqcrXb5rDe-007Wmefhjv9P5-bBB30g4VaKl9AJKXoA/edit#gid=0

**Data profiles:**

Patient:
http://www.hl7.org/fhir/us/core/StructureDefinition-us-core-patient.html

Immunization:
http://www.hl7.org/fhir/us/core/StructureDefinition-us-core-immunization.html


# Sample Bundle

```json
{
  "resourceType": "Bundle",
  "type": "transaction",
  "entry": [
    {
      "fullUrl": "urn:uuid:7fde48c7-f1f4-4af3-a7af-8d94c96d6838",
      "resource": {
        "resourceType": "Patient",
        "identifier": [
          {
            "system": "http://example.ph/national-patient-id",
            "value": "9937454-33"
          }
        ],
        "name": [
          {
            "family": "Cruz",
            "given": [
              "Angelica",
              "Cecelia"
            ]
          }
        ],
        "gender": "female",
        "birthDate": "2011-05-22"
      },
      "request": {
        "method": "PUT",
        "url": "Patient?identifier=http://example.ph/national-patient-id|9937454-33"
      }
    },
    {
      "resource": {
        "resourceType": "Immunization",
        "identifier": [
          {
            "system": "http://example.ph/vaccinations",
            "value": "376-2877"
          }
        ],
        "status": "completed",
        "notGiven": false,
        "vaccineCode": {
          "coding": [
            {
              "display": "DTaP (Diphtheria, Tetanus, Pertussis)"
            }
          ]
        },
        "patient": {
          "reference": "urn:uuid:7fde48c7-f1f4-4af3-a7af-8d94c96d6838"
        },
        "date": "2011-09-12T14:00:03+07:00",
        "primarySource": true,
        "vaccinationProtocol": [
          {
            "series": "2"
          }
        ]
      },
      "request": {
        "method": "PUT",
        "url": "Immunization?identifier=http://example.ph/vaccinations|376-2877"
      }
    }
  ]
}
```