<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 12/02/2015 9:49:07 AM

                  Product            : Shared Health Summary
                  Release            : CDA_Implementation_Guide_v1.0
                  IG Guide Reference : 6.1.2
                  IG Guide Title     : SUBJECT OF CARE
                  Generator Version  : 2.27
                                                                                                                                                                                         
_________________________________________________________________________________________________________________________________________________________________________________________
-->

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix = "cda" uri = "urn:hl7-org:v3"/>
    <ns prefix = "ext" uri = "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix = "xs" uri = "http://www.w3.org/2001/XMLSchema"/>
    <ns prefix = "xsi" uri = "http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-Shared_Health_Summary_SUBJECTOFCARE_6_1_2-errors"
        id="p-Shared_Health_Summary_SUBJECTOFCARE_6_1_2-errors"
        see="#p-Shared_Health_Summary_SUBJECTOFCARE_6_1_2-errors">


        <rule context="/cda:ClinicalDocument">

            <assert test="cda:recordTarget"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'recordTarget' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:recordTarget) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'recordTarget' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget">

            <report test="@typeCode and normalize-space(@typeCode) = ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'recordTarget' tag 'typeCode' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="
                @typeCode and normalize-space(@typeCode) != '' and @typeCode != 'RCT'"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'recordTarget' tag 'typeCode' attribute shall contain the value 'RCT'.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="cda:patientRole"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'patientRole' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:patientRole) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'patientRole' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole">

            <report test="@classCode and normalize-space(@classCode) = ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'patientRole' tag 'classCode' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="
                @classCode and normalize-space(@classCode) != '' and @classCode != 'PAT'"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'patientRole' tag 'classCode' attribute shall contain the value 'PAT'.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="cda:id"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'id' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:id) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'id' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="cda:patient"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant" -
                The 'patient' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:patient) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant" -
                The 'patient' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="cda:addr"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Address" -
                The 'addr' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

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
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care" -
                The 'id' tag 'root' attribute shall be a valid UUID.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient">

            <assert test="ext:asEntityIdentifier"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(ext:asEntityIdentifier) or ext:asEntityIdentifier/ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.800360')]"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Entity Identifier" -
                The 'asEntityIdentifier' tag for 'IHI' is missing.
                "The value of one Entity Identifier SHALL be an Australian IHI." although there could be multiple Entity Identifiers.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="cda:name"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Person Name" -
                The 'name' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="cda:administrativeGenderCode"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:administrativeGenderCode) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="count(cda:birthTime) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Birth Detail / Date of Birth" -
                The 'birthTime' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="cda:birthTime"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Birth Detail / Date of Birth" -
                The 'birthTime' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(ext:multipleBirthInd) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Birth Order" -
                The 'multipleBirthInd' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="count(ext:multipleBirthOrderNumber) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Birth Order" -
                The 'multipleBirthOrderNumber' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="count(ext:deceasedInd) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Death Detail / Date of Death" -
                The 'deceasedInd' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="count(ext:deceasedTime) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Death Detail / Date of Death" -
                The 'deceasedTime' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="count(cda:birthplace) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Country of Birth" -
                The 'birthplace' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="cda:ethnicGroupCode"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:ethnicGroupCode) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:administrativeGenderCode">

            <assert test="@code"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' attribute is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(@code) or normalize-space(@code) != ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test = "not(@code) or normalize-space(@code) = '' or 
                document('TEMPLATE/VALDN/CDAValidate_Vocabs.xml', .)/systems/system[@codeSystemName = 'AS_5017-2006_Health_Care_Client_Identifier_Sex']/code[(@code = current()/@code)]"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' attribute shall be as per AS 5017-2006 Health Care Client Identifier Sex.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="@codeSystem"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystem' attribute is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(@codeSystem) or normalize-space(@codeSystem) != ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystem' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.13.68'"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.13.68'.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="@displayName and normalize-space(@displayName) = ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'displayName' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="@code and normalize-space(@code) != '' and
                @displayName and normalize-space(@displayName) != '' and
                not(document('TEMPLATE/VALDN/CDAValidate_Vocabs.xml', .)/systems/system [@codeSystemName = 'AS_5017-2006_Health_Care_Client_Identifier_Sex']/code[
                    (@code = current()/@code) and (
                    translate(@displayName,  'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') =
                    translate(current()/@displayName, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))])"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'code' and 'displayName' attributes shall match as per AS 5017-2006 Health Care Client Identifier Sex.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="not(@nullFlavor)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'administrativeGenderCode' tag 'nullFlavor' attribute shall not be present.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(cda:translation)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Sex" -
                The 'translation' tag shall not be present within 'administrativeGenderCode'.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:birthTime">

            <assert test="not(@nullFlavor)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Birth Detail / Date of Birth" -
                The 'birthTime' tag 'nullFlavor' attribute shall not be present.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/ext:multipleBirthInd">

            <report test="@value and normalize-space(@value) = ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Birth Order" -
                The 'multipleBirthInd' tag 'value' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/ext:multipleBirthOrderNumber">

            <report test="@value and normalize-space(@value) = ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Birth Order" -
                The 'multipleBirthOrderNumber' tag 'value' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/ext:deceasedInd">

            <assert test="not(@nullFlavor)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Death Detail / Date of Death" -
                The 'deceasedInd' tag 'nullFlavor' attribute shall not be present.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/ext:deceasedTime">

            <assert test="not(@nullFlavor)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Date of Death Detail / Date of Death" -
                The 'deceasedTime' tag 'nullFlavor' attribute shall not be present.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:birthplace">

            <assert test="cda:place"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Country of Birth" -
                The 'place' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:place) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Country of Birth" -
                The 'place' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:birthplace/cda:place">

            <assert test="cda:addr"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Country of Birth" -
                The 'addr' tag is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="count(cda:addr) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Country of Birth" -
                The 'addr' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:birthplace/cda:place/cda:addr">

            <report test="count(cda:country) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Country of Birth" -
                The 'country' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="count(cda:state) > 1"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / State/Territory of Birth" -
                The 'state' tag shall appear only once.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:ethnicGroupCode">

            <assert test="@code"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' attribute is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(@code) or normalize-space(@code) != ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test = "not(@code) or normalize-space(@code) = '' or 
                document('TEMPLATE/VALDN/CDAValidate_Vocabs.xml', .)/systems/system[@codeSystemName = 'METeOR_Indigenous_Status']/code[(@code = current()/@code)]"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' attribute shall be as per METeOR 291036: Indigenous Status.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="@codeSystem"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystem' attribute is missing.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(@codeSystem) or normalize-space(@codeSystem) != ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystem' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.3.879.291036'"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.3.879.291036'.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report test="@displayName and normalize-space(@displayName) = ''"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'displayName' attribute shall contain a value.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="@code and normalize-space(@code) != '' and
                @displayName and normalize-space(@displayName) != '' and
                not(document('TEMPLATE/VALDN/CDAValidate_Vocabs.xml', .)/systems/system [@codeSystemName = 'METeOR_Indigenous_Status']/code[
                    (@code = current()/@code) and (
                    translate(@displayName,  'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') =
                    translate(current()/@displayName, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))])"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'code' and 'displayName' attributes shall match as per METeOR 291036: Indigenous Status.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <assert test="not(@nullFlavor)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'ethnicGroupCode' tag 'nullFlavor' attribute shall not be present.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="not(cda:translation)"
                >Error: Shared Health Summary - 6.1.2 SUBJECT OF CARE -
                "Subject of Care / Participant / Person or Organisation or Device / Person / Demographic Data / Indigenous Status" -
                The 'translation' tag shall not be present within 'ethnicGroupCode'.
                Refer to section 6.1.2 of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

        </rule>


    </pattern>

</schema>
