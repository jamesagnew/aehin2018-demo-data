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
      "fullUrl": "urn:uuid:b325ebf2-7891-4339-ab5a-5a03f342ff53",
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
        "birthDate": "2011-05-22",
        "address": [
          {
            "extension": [
              {
                "url": "http://hl7.org/fhir/StructureDefinition/geolocation",
                "extension": [
                  {
                    "url": "latitude",
                    "valueDecimal": 10.3
                  },
                  {
                    "url": "longitude",
                    "valueDecimal": 123.9
                  }
                ]
              }
            ],
            "line": [
              "Unit 607, Tower 1 Marco Polo Residences"
            ],
            "city": "Cebu City",
            "state": "Cebu",
            "country": "Philippines"
          }
        ],
        "managingOrganization": {
          "reference": "urn:uuid:899204eb-5e7c-4196-a441-82c4e8d5c6de"
        }
      },
      "request": {
        "method": "PUT",
        "url": "Patient?identifier=http://example.ph/national-patient-id|9937454-33"
      }
    },
    {
      "fullUrl": "urn:uuid:899204eb-5e7c-4196-a441-82c4e8d5c6de",
      "resource": {
        "resourceType": "Organization",
        "identifier": [
          {
            "system": "http://example.ph/organizations",
            "value": "123-4"
          }
        ],
        "name": "District Hospital"
      },
      "request": {
        "method": "PUT",
        "url": "Organization?identifier=organizations|123-4"
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
              "system": "http://example.ph/vaccine-codes",
              "code": "OPV",
              "display": "Oral Polio Vaccine"
            }
          ]
        },
        "patient": {
          "reference": "urn:uuid:b325ebf2-7891-4339-ab5a-5a03f342ff53"
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