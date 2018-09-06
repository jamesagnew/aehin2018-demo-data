package ph.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.dstu3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {
    private static final Logger ourLog = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        FhirContext ctx = FhirContext.forDstu3();
        IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseDstu3");
        client.registerInterceptor(new LoggingInterceptor(true));

        // Organization
        Organization org = new Organization();
        org.setId(IdType.newRandomUuid());
        org.setName("District Hospital");
        org.addIdentifier()
                .setSystem("http://example.ph/organizations")
                .setValue("123-4");

        // Create Patient
        Patient patient = new Patient();
        patient.setIdElement(IdType.newRandomUuid());
        patient.addIdentifier()
                .setSystem("http://example.ph/national-patient-id")
                .setValue("9937454-33");

        patient.addName()
                .setFamily("Cruz")
                .addGiven("Angelica")
                .addGiven("Cecelia");
        patient.getBirthDateElement().setValueAsString("2011-05-22");
        patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        patient.addAddress()
                .addLine("Unit 607, Tower 1 Marco Polo Residences")
                .setCity("Cebu City")
                .setState("Cebu")
                .setCountry("Philippines");
        patient.getAddress().get(0)
                .addExtension()
                .setUrl("http://hl7.org/fhir/StructureDefinition/geolocation")
                .addExtension(new Extension()
                        .setUrl("latitude")
                        .setValue(new DecimalType(10.3)))
                .addExtension(new Extension()
                        .setUrl("longitude")
                        .setValue(new DecimalType(123.9)));
        patient.setManagingOrganization(new Reference(org.getId()));

        // Create Vaccination
        Immunization imm = new Immunization();
        imm.addIdentifier()
                .setSystem("http://example.ph/vaccinations")
                .setValue("376-2877");
        imm.getPatient().setReference(patient.getId());
        imm.setStatus(Immunization.ImmunizationStatus.COMPLETED);
        imm.getDateElement().setValueAsString("2011-09-12T14:00:03+07:00");
        imm.setNotGiven(false);
        imm.setPrimarySource(true);
        imm.getVaccineCode()
                .addCoding()
                .setSystem("http://example.ph/vaccine-codes")
                .setCode("OPV")
                .setDisplay("Oral Polio Vaccine");
        imm.addVaccinationProtocol()
                .setSeries("2");

        /*
         * Create transaction
         *
         * This has the following logic:
         *  - Always update the patient
         *  - Always update the immunization
         */
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.TRANSACTION);
        bundle.addEntry()
                .setResource(patient)
                .setFullUrl(patient.getId())
                .getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("Patient?identifier=http://example.ph/national-patient-id|9937454-33");
        bundle.addEntry()
                .setResource(org)
                .setFullUrl(org.getId())
                .getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("Organization?identifier=organizations|123-4");
        bundle.addEntry()
                .setResource(imm)
                .setFullUrl(imm.getId())
                .getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("Immunization?identifier=http://example.ph/vaccinations|376-2877");

        ourLog.info("Input transaction:\n{}", ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));

        client.transaction().withBundle(bundle).execute();

    }
}
