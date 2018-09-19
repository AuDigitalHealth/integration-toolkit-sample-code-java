<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 28/01/2015 3:31:12 PM

                  Product            : PCEHR Prescription Record
                  Release            : CDA_Implementation_Guide_v1.0
                  IG Guide Reference : 6.1.1
                  IG Guide Title     : Subject of Care
                  Generator Version  : 2.27
                                                                                                                                                                                         
_________________________________________________________________________________________________________________________________________________________________________________________
-->

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix = "cda" uri = "urn:hl7-org:v3"/>
    <ns prefix = "ext" uri = "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix = "xs" uri = "http://www.w3.org/2001/XMLSchema"/>
    <ns prefix = "xsi" uri = "http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-PCEHR_Prescription_Record_SubjectofCare_6_1_1-errors"
        id="p-PCEHR_Prescription_Record_SubjectofCare_6_1_1-errors"
        see="#p-PCEHR_Prescription_Record_SubjectofCare_6_1_1-errors">


        <rule context="/cda:ClinicalDocument">

            <assert test="cda:recordTarget"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'recordTarget' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <report test="count(cda:recordTarget) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'recordTarget' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget">

            <report test="@typeCode  and normalize-space(@typeCode ) = ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'recordTarget' tag 'typeCode ' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <report test="
                @typeCode  and normalize-space(@typeCode ) != '' and @typeCode  != 'RCT'"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'recordTarget' tag 'typeCode ' attribute shall contain the value 'RCT'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <assert test="cda:patientRole"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'patientRole' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <report test="count(cda:patientRole) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'patientRole' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole">

            <report test="@classCode and normalize-space(@classCode) = ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'patientRole' tag 'classCode' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <report test="
                @classCode and normalize-space(@classCode) != '' and @classCode != 'PAT'"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care" -
                The 'patientRole' tag 'classCode' attribute shall contain the value 'PAT'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <assert test="cda:id"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / id" -
                The 'id' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <report test="count(cda:id) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / id" -
                The 'id' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <assert test="cda:patient"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant" -
                The 'patient' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <report test="count(cda:patient) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant" -
                The 'patient' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:id">

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
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / id" -
                The 'id' tag 'root' attribute shall be a valid UUID.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient">

            <assert test="ext:asEntityIdentifier"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(ext:asEntityIdentifier) or ext:asEntityIdentifier/ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.800360')]"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag for 'IHI' is missing.
                "The value of one Entity Identifier SHALL be an Australian IHI." although there could be multiple Entity Identifiers.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="cda:name"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Person Name" -
                The 'name' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="cda:administrativeGenderCode"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <report test="count(cda:administrativeGenderCode) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <assert test="cda:birthTime"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Birth" -
                The 'birthTime' tag is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <report test="count(cda:birthTime) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Birth" -
                The 'birthTime' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <report test="count(ext:multipleBirthInd) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Birth Order" -
                The 'multipleBirthInd' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <report test="count(ext:multipleBirthOrderNumber) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Birth Order" -
                The 'multipleBirthOrderNumber' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

            <report test="count(cda:ethnicGroupCode) > 1"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag shall appear only once.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:administrativeGenderCode">

            <assert test="not(@code) or normalize-space(@code) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@code"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test = "not(@code) or normalize-space(@code) = '' or 
                document('\TEMPLATE\VALDN\CDAValidate_Vocabs.xml', .)/systems/system[@codeSystemName = 'AS_5017-2006_Health_Care_Client_Identifier_Sex']/code[(@code = current()/@code)]"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' attribute shall be as per AS 5017-2006 Health Care Client Identifier Sex.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@codeSystem) or normalize-space(@codeSystem) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystem' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.13.68'"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.13.68'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@codeSystem"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystem' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@codeSystemName) or normalize-space(@codeSystemName) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystemName' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@codeSystemName"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystemName' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'AS 5017-2006 Health Care Client Identifier Sex'"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystemName' attribute shall contain the value 'AS 5017-2006 Health Care Client Identifier Sex'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@displayName) or normalize-space(@displayName) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'displayName' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@displayName"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'displayName' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@code) or normalize-space(@code) = '' or
                not(@displayName) or normalize-space(@displayName) = '' or
                document('\TEMPLATE\VALDN\CDAValidate_Vocabs.xml', .)/systems/system [@codeSystemName = 'AS_5017-2006_Health_Care_Client_Identifier_Sex']/code[
                    (@code = current()/@code) and (
                    translate(@displayName,  'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') =
                    translate(current()/@displayName, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' and 'displayName' attributes shall match as per AS 5017-2006 Health Care Client Identifier Sex.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@nullFlavor)"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'nullFlavor' attribute shall not be present.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(cda:translation)"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'translation' tag shall not be present within 'administrativeGenderCode'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:ethnicGroupCode">

            <assert test="@code"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test = "not(@code) or normalize-space(@code) = '' or 
                document('\TEMPLATE\VALDN\CDAValidate_Vocabs.xml', .)/systems/system[@codeSystemName = 'METeOR_Indigenous_Status']/code[(@code = current()/@code)]"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' attribute shall be as per METeOR 291036: Indigenous Status.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@code) or normalize-space(@code) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.3.879.291036'"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.3.879.291036'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@codeSystem"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystem' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@codeSystem) or normalize-space(@codeSystem) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystem' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'METeOR Indigenous Status'"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystemName' attribute shall contain the value 'METeOR Indigenous Status'.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@codeSystemName"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystemName' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@codeSystemName) or normalize-space(@codeSystemName) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystemName' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@code) or normalize-space(@code) = '' or
                not(@displayName) or normalize-space(@displayName) = '' or
                document('\TEMPLATE\VALDN\CDAValidate_Vocabs.xml', .)/systems/system [@codeSystemName = 'METeOR_Indigenous_Status']/code[
                    (@code = current()/@code) and (
                    translate(@displayName,  'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') =
                    translate(current()/@displayName, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' and 'displayName' attributes shall match as per METeOR 291036: Indigenous Status.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="@displayName"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'displayName' attribute is missing.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

            <assert test="not(@displayName) or normalize-space(@displayName) != ''"
                >Error: PCEHR Prescription Record - 6.1.1 Subject of Care -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'displayName' attribute shall contain a value.
                Refer to section 6.1.1 of the
                PCEHR_Prescription_Record_CDA_Implementation_Guide_v1.0.</assert>

        </rule>


    </pattern>

</schema>
