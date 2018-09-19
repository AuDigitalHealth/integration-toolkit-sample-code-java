<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 30/09/2013 2:26:00 PM

                  Product            : Dispense Record
                  Release            : CDA_Implementation_Guide_v1.0
                  IG Guide Reference : 7.4
                  IG Guide Title     : Dispensing Organization
                  Generator Version  : 2.27
                                                                                                                                                                                         
_________________________________________________________________________________________________________________________________________________________________________________________
-->

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix = "cda" uri = "urn:hl7-org:v3"/>
    <ns prefix = "ext" uri = "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix = "xs" uri = "http://www.w3.org/2001/XMLSchema"/>
    <ns prefix = "xsi" uri = "http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-Dispense_Record_DispensingOrganization_7_4-errors"
        id="p-Dispense_Record_DispensingOrganization_7_4-errors"
        see="#p-Dispense_Record_DispensingOrganization_7_4-errors">


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter">

            <assert test="cda:location"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation" -
                The 'location' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:location) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation" -
                The 'location' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location">

            <report test="@typeCode and normalize-space(@typeCode) = ''"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation" -
                The 'location' tag 'typeCode' attribute shall contain a value.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <report test="
                @typeCode and normalize-space(@typeCode) != '' and @typeCode != 'LOC'"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation" -
                The 'location' tag 'typeCode' attribute shall contain the value 'LOC'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <assert test="cda:healthCareFacility"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participation Type" -
                The 'healthCareFacility' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:healthCareFacility) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participation Type" -
                The 'healthCareFacility' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility">

            <report test="count(cda:id) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participation Type" -
                The 'id' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <assert test="cda:code[@code = 'PHARM']"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag is missing for ' Role'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:code[@code = 'PHARM']) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag shall appear only once for ' Role'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <assert test="cda:serviceProviderOrganization"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant" -
                The 'serviceProviderOrganization' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:serviceProviderOrganization) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant" -
                The 'serviceProviderOrganization' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:id">

            <report test="
                contains(@root, '-') and not(
                string-length(@root) = 36 and
                substring(@root,  9, 1) = '-' and substring(@root, 14, 1) = '-' and
                substring(@root, 19, 1) = '-' and substring(@root, 24, 1) = '-' and
                translate(substring(@root,  1,  8), '0123456789ABCDEFabcdef', '0000000000000000000000') = '00000000' and
                translate(substring(@root, 10,  4), '0123456789ABCDEFabcdef', '0000000000000000000000') = '0000' and
                translate(substring(@root, 15,  4), '0123456789ABCDEFabcdef', '0000000000000000000000') = '0000' and
                translate(substring(@root, 20,  4), '0123456789ABCDEFabcdef', '0000000000000000000000') = '0000' and
                translate(substring(@root, 25, 12), '0123456789ABCDEFabcdef', '0000000000000000000000') = '000000000000')"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participation Type" -
                The 'id' tag 'root' attribute shall be a valid UUID.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:code[@code = 'PHARM']">

            <assert test="@codeSystem"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.1.11.17660'"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.1.11.17660'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="@codeSystemName"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'HL7 ServiceDeliveryLocationRoleType'"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'HL7 ServiceDeliveryLocationRoleType'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="@displayName"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Pharmacy'"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'code' tag 'displayName' attribute shall contain the value 'Pharmacy'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="not(cda:translation)"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Role" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:serviceProviderOrganization">

            <assert test="cda:asOrganizationPartOf"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant" -
                The 'asOrganizationPartOf' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:asOrganizationPartOf) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant" -
                The 'asOrganizationPartOf' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <report test="count(cda:name) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Person or Organisation or Device / Organisation / Department/Unit" -
                The 'name' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:serviceProviderOrganization/cda:asOrganizationPartOf">

            <assert test="cda:wholeOrganization"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant" -
                The 'wholeOrganization' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:wholeOrganization) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant" -
                The 'wholeOrganization' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:serviceProviderOrganization/cda:asOrganizationPartOf/cda:wholeOrganization">

            <assert test="ext:asEntityIdentifier"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="not(ext:asEntityIdentifier) or ext:asEntityIdentifier/ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.800362')]"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag for 'HPI-O' is missing.
                "The value of one Entity Identifier SHALL be an Australian HPI-O." although there could be multiple Entity Identifiers.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(ext:asEntityIdentifier) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <assert test="cda:addr"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Address" -
                The 'addr' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:addr) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Address" -
                The 'addr' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <assert test="cda:telecom"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Electronic Communication Detail" -
                The 'telecom' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="cda:name"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Person or Organisation or Device / Organisation / Organisation Name" -
                The 'name' tag is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="count(cda:name) > 1"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Person or Organisation or Device / Organisation / Organisation Name" -
                The 'name' tag shall appear only once.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:serviceProviderOrganization/cda:asOrganizationPartOf/cda:wholeOrganization/cda:addr">

            <assert test="@use"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Address" -
                The 'addr' tag 'use' attribute is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <report test="@use and normalize-space(@use) = ''"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Address" -
                The 'addr' tag 'use' attribute shall contain a value.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

            <report test="
                @use and normalize-space(@use) != '' and @use != 'WP'"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Address" -
                The 'addr' tag 'use' attribute shall contain the value 'WP'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:componentOf/cda:encompassingEncounter/cda:location/cda:healthCareFacility/cda:serviceProviderOrganization/cda:asOrganizationPartOf/cda:wholeOrganization/cda:name">

            <report test="@use and normalize-space(@use) = ''"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Person or Organisation or Device / Organisation / Organisation Name" -
                The 'name' tag 'use' attribute shall contain a value.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</report>

        </rule>


    </pattern>

</schema>
