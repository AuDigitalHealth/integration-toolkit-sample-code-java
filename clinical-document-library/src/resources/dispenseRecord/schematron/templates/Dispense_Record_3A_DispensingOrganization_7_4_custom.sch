<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 25/07/2013 8:57:47 AM

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

    <pattern name="p-Dispense_Record_3A_DispensingOrganization_7_4_custom-errors"
        id="p-Dispense_Record_3A_DispensingOrganization_7_4_custom-errors"
        see="#p-Dispense_Record_3A_DispensingOrganization_7_4_custom-errors">

   

        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/ext:coverage2/ext:entitlement/ext:participant[ext:participantRole/@classCode = 'SDLOC']">

            <assert test="@typeCode"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Entitlement" -
                The 'participant' tag 'typeCode' attribute is missing.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Entitlement" -
                The 'participant' tag 'typeCode' attribute shall contain a value.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'HLD'"
                >Error: Dispense Record - 7.4 Dispensing Organization -
                "Dispensing Organisation / Participant / Entitlement" -
                The 'participant' tag 'typeCode' attribute shall contain the value 'HLD'.
                Refer to section 7.4 of the
                Dispense_Record_CDA_Implementation_Guide_v0.4.</assert>

        </rule>


    </pattern>

</schema>
