<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 23/10/2013 1:37:42 PM

                  Product            : e-Prescription
                  Release            : CDA_Implementation_Guide_v1.0
                  IG Guide Reference : 8.2
                  IG Guide Title     : Prescription item
                  Generator Version  : 2.27
                                                                                                                                                                                         
_________________________________________________________________________________________________________________________________________________________________________________________
-->

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix = "cda" uri = "urn:hl7-org:v3"/>
    <ns prefix = "ext" uri = "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix = "xs" uri = "http://www.w3.org/2001/XMLSchema"/>
    <ns prefix = "xsi" uri = "http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-e-Prescription_3A_Prescriptionitem_8_2-errors"
        id="p-e-Prescription_3A_Prescriptionitem_8_2-errors"
        see="#p-e-Prescription_3A_Prescriptionitem_8_2-errors">


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']">

            <assert test="cda:entry[cda:substanceAdministration/@classCode = 'SBADM']"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'entry' tag is missing for 'Prescription Item'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:entry[cda:substanceAdministration/@classCode = 'SBADM']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'entry' tag shall appear only once for 'Prescription Item'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:author"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Written" -
                The 'author' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:author) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Written" -
                The 'author' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:entry[cda:observation/cda:code/@code = '103.10104']"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'entry' tag is missing for ' DateTime Prescription Expires'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:entry[cda:observation/cda:code/@code = '103.10104']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'entry' tag shall appear only once for ' DateTime Prescription Expires'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry[cda:substanceAdministration/@classCode = 'SBADM']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'entry' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'entry' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'DRIV'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'entry' tag 'typeCode' attribute shall contain the value 'DRIV'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:substanceAdministration) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'substanceAdministration' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']">

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'substanceAdministration' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'substanceAdministration' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'RQO'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'substanceAdministration' tag 'moodCode' attribute shall contain the value 'RQO'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="cda:statusCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'statusCode' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:statusCode) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'statusCode' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Prescription Item Identifier" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Prescription Item Identifier" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:consumable"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'consumable' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:consumable) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'consumable' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:act/cda:code/@code = '103.16272']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'entryRelationship' tag shall appear only once for ' Formula'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:text) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Directions" -
                The 'text' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:doseQuantity) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity" -
                The 'doseQuantity' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:act/cda:code/@code = '246205007']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'entryRelationship' tag shall appear only once for ' Quantity Description'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:act/cda:code/@code = '246512002']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'entryRelationship' tag shall appear only once for ' Timing Description'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:precondition) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'precondition' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[ext:controlAct/ext:code/@code = '103.16436']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'entryRelationship' tag shall appear only once for ' Start Criterion'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[ext:controlAct/ext:code/@code = '103.16434']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'entryRelationship' tag shall appear only once for ' Stop Criterion'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:maxDoseQuantity) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Number of Administrations" -
                The 'maxDoseQuantity' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:observation/cda:code/@code = '103.16440']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'entryRelationship' tag shall appear only once for ' LongTerm'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:routeCode) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Administration Details Route" -
                The 'routeCode' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:approachSiteCode) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Administration Details / Anatomical Site" -
                The 'approachSiteCode' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:methodCode) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Administration Details / Medication Delivery Method" -
                The 'methodCode' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:supply/@classCode = 'SPLY']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity" -
                The 'entryRelationship' tag shall appear only once for ' Quantity'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:entryRelationship[cda:supply/@classCode = 'SPLY']"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity" -
                The 'entryRelationship' tag is missing for ' Quantity'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="cda:repeatNumber"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Maximum Number of Repeats" -
                The 'repeatNumber' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:repeatNumber) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Maximum Number of Repeats" -
                The 'repeatNumber' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:act/cda:code/@code = '103.10141']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'entryRelationship' tag shall appear only once for ' Reason for Therapeutic Good'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:act/cda:code/@code = '103.16044']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'entryRelationship' tag shall appear only once for ' Additional Comments'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:reference[cda:externalAct/cda:code/@code = '103.16444']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'reference' tag shall appear only once for ' Medication Instruction Identifier'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:statusCode">

            <assert test="@code"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'statusCode' tag 'code' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@code) or normalize-space(@code) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'statusCode' tag 'code' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@code) or normalize-space(@code) = '' or @code = 'active'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item" -
                The 'statusCode' tag 'code' attribute shall contain the value 'active'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:author">

            <assert test="cda:assignedAuthor"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Written" -
                The 'assignedAuthor' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:assignedAuthor) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Written" -
                The 'assignedAuthor' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:author/cda:assignedAuthor">

            <assert test="cda:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Written" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Written" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry[cda:observation/cda:code/@code = '103.10104']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'entry' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'entry' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'DRIV'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'entry' tag 'typeCode' attribute shall contain the value 'DRIV'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:observation) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:observation[cda:code/@code = '103.10104']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'OBS'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag 'classCode' attribute shall contain the value 'OBS'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'observation' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:effectiveTime"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'effectiveTime' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:effectiveTime) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'effectiveTime' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:observation/cda:code[@code = '103.10104']">

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'DateTime Prescription Expires'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'code' tag 'displayName' attribute shall contain the value 'DateTime Prescription Expires'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:observation[cda:code/@code = '103.10104']/cda:effectiveTime">

            <assert test="@value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'effectiveTime' tag 'value' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@value) or normalize-space(@value) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'effectiveTime' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:observation[cda:code/@code = '103.10104']/cda:id">

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
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / DateTime Prescription Expires" -
                The 'id' tag 'root' attribute shall be a valid UUID.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable">

            <assert test="cda:manufacturedProduct"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'manufacturedProduct' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:manufacturedProduct) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'manufacturedProduct' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct">

            <assert test="cda:manufacturedMaterial"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'manufacturedMaterial' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:manufacturedMaterial) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'manufacturedMaterial' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:subjectOf1[ext:policy/ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16085']"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'subjectOf1' tag is missing for ' Grounds for Concurrent Supply'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:subjectOf1[ext:policy/ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16085']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'subjectOf1' tag shall appear only once for ' Grounds for Concurrent Supply'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:subjectOf1[ext:policy/ext:code/@code = '103.16719']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'subjectOf1' tag shall appear only once for ' PBS/RPBS Authority Prescription Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:subjectOf1[ext:policy/ext:code/@code = '103.16718']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'subjectOf1' tag shall appear only once for ' Streamlined Authority Approval Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:subjectOf1[ext:policy/ext:code/@code = '103.16018']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'subjectOf1' tag shall appear only once for ' State Authority Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial">

            <assert test="cda:code"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'code' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Therapeutic Good Identification" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:formCode) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Dose Unit" -
                The 'formCode' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Item Code" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/cda:code">

            <report test="count(cda:translation) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Item Code" -
                The 'translation' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:act/cda:code/@code = '103.16272']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:act) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.16272']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'INFRM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag 'classCode' attribute shall contain the value 'INFRM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'RQO'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'act' tag 'moodCode' attribute shall contain the value 'RQO'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:text"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'text' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:text) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'text' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="cda:code[@code = '103.16272']">

            <assert test="
                count(/cda:ClinicalDocument//cda:code[@code = '103.16272']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:act/cda:code[@code='103.16272'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/act/code[@code = '103.16272']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Formula'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'code' tag 'displayName' attribute shall contain the value 'Formula'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.16272']/cda:id">

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
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'id' tag 'root' attribute shall be a valid UUID.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.16272']/cda:text">

            <report test="@xsi:type and normalize-space(@xsi:type) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'text' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="
                @xsi:type and normalize-space(@xsi:type) != '' and 
                normalize-space(@xsi:type) != 'ST' and
                not(substring-before(normalize-space(@xsi:type), ':') != '' and
                    substring-after(normalize-space(@xsi:type), ':') = 'ST')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Formula" -
                The 'text' tag 'xsi:type' attribute shall contain the value 'ST'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:text">

            <report test="@xsi:type and normalize-space(@xsi:type) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Directions" -
                The 'text' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="
                @xsi:type and normalize-space(@xsi:type) != '' and 
                normalize-space(@xsi:type) != 'ST' and
                not(substring-before(normalize-space(@xsi:type), ':') != '' and
                    substring-after(normalize-space(@xsi:type), ':') = 'ST')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Directions" -
                The 'text' tag 'xsi:type' attribute shall contain the value 'ST'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:doseQuantity">

            <assert test="@value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity" -
                The 'doseQuantity' tag 'value' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@value) or normalize-space(@value) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity" -
                The 'doseQuantity' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="@unit and normalize-space(@unit) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity" -
                The 'doseQuantity' tag 'unit' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:act/cda:code/@code = '246205007']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:act) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '246205007']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'ACT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag 'classCode' attribute shall contain the value 'ACT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'act' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act/cda:code[@code = '246205007']">

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.6.96'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.6.96'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'SNOMED CT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'SNOMED CT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Quantity'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'code' tag 'displayName' attribute shall contain the value 'Quantity'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Structured Dose / Quantity Description" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:act/cda:code/@code = '246512002']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:act) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '246512002']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'ACT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag 'classCode' attribute shall contain the value 'ACT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'act' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:text"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'text' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:text) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'text' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="not(cda:text) or normalize-space(cda:text) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'text' tag shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="cda:code[@code = '246512002']">

            <assert test="
                count(/cda:ClinicalDocument//cda:code[@code = '246512002']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:act/cda:code[@code='246512002'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/act/code[@code = '246512002']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.6.96'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.6.96'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'SNOMED CT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'SNOMED CT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Timing'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'code' tag 'displayName' attribute shall contain the value 'Timing'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Timing Description" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:precondition">

            <assert test="cda:criterion"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'criterion' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:criterion) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'criterion' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:precondition/cda:criterion">

            <report test="count(cda:value) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'value' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:precondition/cda:criterion/cda:value">

            <assert test="@xsi:type"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'value' tag 'xsi:type' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@xsi:type) or normalize-space(@xsi:type) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'value' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@xsi:type) or normalize-space(@xsi:type) = '' or 
                normalize-space(@xsi:type) = 'BL' or
                (substring-before(normalize-space(@xsi:type), ':') != '' and
                substring-after(normalize-space(@xsi:type), ':') = 'BL')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / PRN" -
                The 'value' tag 'xsi:type' attribute shall contain the value 'BL'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[ext:controlAct/ext:code/@code = '103.16436']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:controlAct) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/ext:controlAct[ext:code/@code = '103.16436']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'CACT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag 'classCode' attribute shall contain the value 'CACT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'controlAct' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:effectiveTime) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Date" -
                The 'effectiveTime' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.16436']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.16436']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/ext:controlAct/ext:code[@code='103.16436'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/controlAct/code[@code = '103.16436']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Start Criterion'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'code' tag 'displayName' attribute shall contain the value 'Start Criterion'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Start Criterion" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[ext:controlAct/ext:code/@code = '103.16434']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:controlAct) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/ext:controlAct[ext:code/@code = '103.16434']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'CACT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag 'classCode' attribute shall contain the value 'CACT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'controlAct' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:effectiveTime) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Date" -
                The 'effectiveTime' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.16434']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.16434']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/ext:controlAct/ext:code[@code='103.16434'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/controlAct/code[@code = '103.16434']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Stop Criterion'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'code' tag 'displayName' attribute shall contain the value 'Stop Criterion'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / Stop Criterion" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:observation/cda:code/@code = '103.16440']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:observation) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:observation[cda:code/@code = '103.16440']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'OBS'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag 'classCode' attribute shall contain the value 'OBS'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'observation' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'value' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:value) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'value' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="cda:code[@code = '103.16440']">

            <assert test="
                count(/cda:ClinicalDocument//cda:code[@code = '103.16440']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:observation/cda:code[@code='103.16440'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/observation/code[@code = '103.16440']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Long-Term'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'code' tag 'displayName' attribute shall contain the value 'Long-Term'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:observation[cda:code/@code = '103.16440']/cda:value">

            <assert test="@xsi:type"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'value' tag 'xsi:type' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@xsi:type) or normalize-space(@xsi:type) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'value' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@xsi:type) or normalize-space(@xsi:type) = '' or 
                normalize-space(@xsi:type) = 'BL' or
                (substring-before(normalize-space(@xsi:type), ':') != '' and
                substring-after(normalize-space(@xsi:type), ':') = 'BL')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Timing / LongTerm" -
                The 'value' tag 'xsi:type' attribute shall contain the value 'BL'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:supply/@classCode = 'SPLY']">

            <report test="count(cda:supply[@classCode = 'SPLY']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity" -
                The 'supply' tag shall appear only once for ' Quantity'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']">

            <report test="count(cda:quantity) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity" -
                The 'quantity' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:entryRelationship[cda:act/cda:code/@code = '246205007']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'entryRelationship' tag shall appear only once for ' Quantity Description'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:subjectOf2[ext:substitutionPermission/ext:code/@code = 'N']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'subjectOf2' tag shall appear only once for ' Brand Substitue Not Allowed'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:effectiveTime) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'effectiveTime' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:coverage[ext:policyOrAccount/ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16095']"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'coverage' tag is missing for ' Medical Benefit Category Type'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:coverage[ext:policyOrAccount/ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16095']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'coverage' tag shall appear only once for ' Medical Benefit Category Type'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:coverage[ext:policyOrAccount/ext:code/@code = '103.16095.3']"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'coverage' tag is missing for ' PBS Close the Gap Benefit'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:coverage[ext:policyOrAccount/ext:code/@code = '103.16095.3']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'coverage' tag shall appear only once for ' PBS Close the Gap Benefit'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:product) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Manufacturer Code" -
                The 'product' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:coverage[ext:policyOrAccount/ext:code/@code = '103.10159']) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'coverage' tag shall appear only once for ' PBS/RPBS Authority Approval Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Dispense Item Identifier" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:quantity">

            <report test="@value and normalize-space(@value) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity" -
                The 'quantity' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="@unit and normalize-space(@unit) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity" -
                The 'quantity' tag 'unit' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:product/cda:manufacturedProduct/cda:manufacturedMaterial">

            <report test="count(ext:formCode) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Dispensing Unit" -
                The 'formCode' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:entryRelationship[cda:act/cda:code/@code = '246205007']">

            <report test="count(cda:act) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:entryRelationship/cda:act[cda:code/@code = '246205007']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'INFRM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag 'classCode' attribute shall contain the value 'INFRM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'INT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'act' tag 'moodCode' attribute shall contain the value 'INT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:text"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'text' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:text) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'text' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="not(cda:text) or normalize-space(cda:text) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'text' tag shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:entryRelationship/cda:act/cda:code[@code = '246205007']">

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.6.96'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.6.96'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'SNOMED CT-AU'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'SNOMED CT-AU'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Quantity'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'code' tag 'displayName' attribute shall contain the value 'Quantity'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / QUANTITY TO DISPENSE / Quantity Description" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:subjectOf2[ext:substitutionPermission/ext:code/@code = 'N']">

            <report test="count(ext:substitutionPermission) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:subjectOf2/ext:substitutionPermission[ext:code/@code = 'N']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'SUBST'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag 'classCode' attribute shall contain the value 'SUBST'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'substitutionPermission' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = 'N']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = 'N']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:supply[@classCode='SPLY']/ext:subjectOf2/ext:substitutionPermission/ext:code[@code = 'N'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/supply[@classCode = 'SPLY']/subjectOf2/substitutionPermission/code[@code = 'N']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '2.16.840.1.113883.5.1070'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag 'codeSystem' attribute shall contain the value '2.16.840.1.113883.5.1070'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'HL7:SubstanceAdminSubstitution'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'HL7:SubstanceAdminSubstitution'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Therapeutic'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'code' tag 'displayName' attribute shall contain the value 'Therapeutic'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Brand Substitue Not Allowed" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:repeatNumber">

            <assert test="cda:high"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Maximum Number of Repeats" -
                The 'high' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:high) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Maximum Number of Repeats" -
                The 'high' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:repeatNumber/cda:high">

            <assert test="@value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Maximum Number of Repeats" -
                The 'high' tag 'value' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@value) or normalize-space(@value) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Maximum Number of Repeats" -
                The 'high' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:effectiveTime">

            <assert test="@xsi:type"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'effectiveTime' tag 'xsi:type' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@xsi:type) or normalize-space(@xsi:type) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'effectiveTime' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@xsi:type) or normalize-space(@xsi:type) = '' or 
                normalize-space(@xsi:type) = 'PIVL_TS' or
                (substring-before(normalize-space(@xsi:type), ':') != '' and
                substring-after(normalize-space(@xsi:type), ':') = 'PIVL_TS')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'effectiveTime' tag 'xsi:type' attribute shall contain the value 'PIVL_TS'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="cda:period"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'period' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:period) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'period' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:effectiveTime/cda:period">

            <assert test="@value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'period' tag 'value' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@value) or normalize-space(@value) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'period' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@unit"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'period' tag 'unit' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@unit) or normalize-space(@unit) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Minimum Interval Between Repeats" -
                The 'period' tag 'unit' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage[ext:policyOrAccount/ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16095']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'coverage' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'coverage' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COVBY'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'coverage' tag 'typeCode' attribute shall contain the value 'COVBY'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:policyOrAccount) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount[ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16095']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'COV'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag 'classCode' attribute shall contain the value 'COV'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'policyOrAccount' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount/ext:code[@codeSystem = '1.2.36.1.2001.1001.101.104.16095']">

            <report test="
                @codeSystemName and normalize-space(@codeSystemName) != '' and @codeSystemName != 'NCTIS Medical Benefit Category Type Values'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Medical Benefit Category Type Values'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test = "not(@code) or normalize-space(@code) = '' or 
                document('CDAValidate_Vocabs.xml')/systems/system[@codeSystemName = 'Medical_Benefit_Category_Type']/code[(@code = current()/@code)]"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'code' tag 'code' attribute shall be as per NCTIS: ADMIN CODES Medical Benefit Category Type.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="@displayName and normalize-space(@displayName) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'code' tag 'displayName' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="@code and normalize-space(@code) != '' and
                @displayName and normalize-space(@displayName) != '' and
                not(document('CDAValidate_Vocabs.xml')/systems/system [@codeSystemName = 'Medical_Benefit_Category_Type']/code[
                    (@code = current()/@code) and (
                    translate(@displayName,  'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') =
                    translate(current()/@displayName, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medical Benefit Category Type" -
                The 'code' tag 'code' and 'displayName' attributes shall match as per NCTIS:ADMIN CODES Medical Benefit Category Type.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage[ext:policyOrAccount/ext:code/@code = '103.16095.3']">

            <report test="count(ext:policyOrAccount) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount[ext:code/@code = '103.16095.3']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'COV'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag 'classCode' attribute shall contain the value 'COV'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'policyOrAccount' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="ext:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.16095.3']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.16095.3']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount/ext:code[@code='103.16095.3'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/supply[@classCode = 'SPLY']/coverage/policyOrAccount/code[@code = '103.16095.3']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'PBS Close the Gap Benefit'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'code' tag 'displayName' attribute shall contain the value 'PBS Close the Gap Benefit'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS Close the Gap Benefit" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:product">

            <assert test="cda:manufacturedProduct"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Manufacturer Code" -
                The 'manufacturedProduct' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:manufacturedProduct) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Manufacturer Code" -
                The 'manufacturedProduct' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:product/cda:manufacturedProduct">

            <report test="count(cda:manufacturerOrganization) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Manufacturer Code" -
                The 'manufacturerOrganization' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/cda:product/cda:manufacturedProduct/cda:manufacturerOrganization">

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Manufacturer Code" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/ext:asIngredient">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'asIngredient' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'asIngredient' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'INGR'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'asIngredient' tag 'classCode' attribute shall contain the value 'INGR'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="ext:ingredientManufacturedMaterial"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:ingredientManufacturedMaterial) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/ext:asIngredient/ext:ingredientManufacturedMaterial">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'MMAT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag 'classCode' attribute shall contain the value 'MMAT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@determinerCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag 'determinerCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@determinerCode) or normalize-space(@determinerCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag 'determinerCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@determinerCode) or normalize-space(@determinerCode) = '' or @determinerCode = 'KIND'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT" -
                The 'ingredientManufacturedMaterial' tag 'determinerCode' attribute shall contain the value 'KIND'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="ext:code"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Name" -
                The 'code' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Name" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:quantity"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'quantity' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:quantity) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'quantity' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/ext:asIngredient/ext:ingredientManufacturedMaterial/ext:quantity">

            <assert test="cda:numerator"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'numerator' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:numerator) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'numerator' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:denominator"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'denominator' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:denominator) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'denominator' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/ext:asIngredient/ext:ingredientManufacturedMaterial/ext:quantity/cda:denominator">

            <assert test="@value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'denominator' tag 'value' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@value) or normalize-space(@value) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'denominator' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@value) or normalize-space(@value) = '' or @value = '1'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS EXTEMPORANGEOUS PREPARATION DESCRIPTION / PBS EXTEMPORANGEOUS INGREDIENT / PBS Extemporaneous Ingredient Quantity" -
                The 'denominator' tag 'value' attribute shall contain the value '1'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1[ext:policy/ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16085']">

            <report test="count(ext:policy) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@codeSystem = '1.2.36.1.2001.1001.101.104.16085']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'JURISPOL'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag 'classCode' attribute shall contain the value 'JURISPOL'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'policy' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy/ext:code[@codeSystem = '1.2.36.1.2001.1001.101.104.16085']">

            <report test="@codeSystemName and normalize-space(@codeSystemName) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'code' tag 'codeSystemName' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="
                @codeSystemName and normalize-space(@codeSystemName) != '' and @codeSystemName != 'NCTIS Concurrent Supply Grounds Values'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Concurrent Supply Grounds Values'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test = "not(@code) or normalize-space(@code) = '' or 
                document('CDAValidate_Vocabs.xml')/systems/system[@codeSystemName = 'Concurrent_Supply_Grounds']/code[(@code = current()/@code)]"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'code' tag 'code' attribute shall be as per Concurrent Supply Grounds.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="@displayName and normalize-space(@displayName) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'code' tag 'displayName' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="@code and normalize-space(@code) != '' and
                @displayName and normalize-space(@displayName) != '' and
                not(document('CDAValidate_Vocabs.xml')/systems/system [@codeSystemName = 'Concurrent_Supply_Grounds']/code[
                    (@code = current()/@code) and (
                    translate(@displayName,  'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') =
                    translate(current()/@displayName, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'code' tag 'code' and 'displayName' attributes shall match as per Concurrent Supply Grounds.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Grounds for Concurrent Supply" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1[ext:policy/ext:code/@code = '103.16719']">

            <report test="count(ext:policy) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'policy' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@code = '103.16719']">

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'policy' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'policy' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'policy' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.16719']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.16719']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy/ext:code[@code='103.16719'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/consumable/manufacturedProduct/subjectOf1/policy/code[@code = '103.16719']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'PBS/RPBS Authority Prescription Number'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'code' tag 'displayName' attribute shall contain the value 'PBS/RPBS Authority Prescription Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@code = '103.16719']/ext:id">

            <assert test="@root"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag 'root' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@root) or normalize-space(@root) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag 'root' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@root) or normalize-space(@root) = '' or @root = '1.2.36.1.5001.1.1.3.2.2'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag 'root' attribute shall contain the value '1.2.36.1.5001.1.1.3.2.2'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@extension"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag 'extension' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@extension) or normalize-space(@extension) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Prescription Number" -
                The 'id' tag 'extension' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage[ext:policyOrAccount/ext:code/@code = '103.10159']">

            <report test="count(ext:policyOrAccount) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount[ext:code/@code = '103.10159']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'COV'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag 'classCode' attribute shall contain the value 'COV'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'policyOrAccount' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.10159']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.10159']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount/ext:code[@code='103.10159'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/supply[@classCode = 'SPLY']/coverage/policyOrAccount/code[@code = '103.10159']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'PBS/RPBS Authority Approval Number'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'code' tag 'displayName' attribute shall contain the value 'PBS/RPBS Authority Approval Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:supply[@classCode = 'SPLY']/ext:coverage/ext:policyOrAccount[ext:code/@code = '103.10159']/ext:id">

            <assert test="@root"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag 'root' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@root) or normalize-space(@root) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag 'root' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@root) or normalize-space(@root) = '' or @root = '1.2.36.1.5001.1.1.3.2.3'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag 'root' attribute shall contain the value '1.2.36.1.5001.1.1.3.2.3'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@extension"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag 'extension' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@extension) or normalize-space(@extension) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / PBS/RPBS Authority Approval Number" -
                The 'id' tag 'extension' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1[ext:policy/ext:code/@code = '103.16718']">

            <report test="count(ext:policy) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'policy' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@code = '103.16718']">

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'policy' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'policy' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'policy' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.16718']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.16718']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy/ext:code[@code='103.16718'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/consumable/manufacturedProduct/subjectOf1/policy/code[@code = '103.16718']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Streamlined Authority Approval Number'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'code' tag 'displayName' attribute shall contain the value 'Streamlined Authority Approval Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@code = '103.16718']/ext:id">

            <assert test="@root"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag 'root' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@root) or normalize-space(@root) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag 'root' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@root) or normalize-space(@root) = '' or @root = '1.2.36.1.2001.1005.24'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag 'root' attribute shall contain the value '1.2.36.1.2001.1005.24'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@extension"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag 'extension' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@extension) or normalize-space(@extension) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Streamlined Authority Approval Number" -
                The 'id' tag 'extension' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1[ext:policy/ext:code/@code = '103.16018']">

            <report test="count(ext:policy) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@code = '103.16018']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'JURISPOL'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag 'classCode' attribute shall contain the value 'JURISPOL'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'PERM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'policy' tag 'moodCode' attribute shall contain the value 'PERM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="ext:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(ext:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="ext:code[@code = '103.16018']">

            <assert test="
                count(/cda:ClinicalDocument//ext:code[@code = '103.16018']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy/ext:code[@code='103.16018'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/consumable/manufacturedProduct/subjectOf1/policy/code[@code = '103.16018']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'State Authority Number'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'code' tag 'displayName' attribute shall contain the value 'State Authority Number'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:consumable/cda:manufacturedProduct/ext:subjectOf1/ext:policy[ext:code/@code = '103.16018']/ext:id">

            <assert test="@root"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'id' tag 'root' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@root) or normalize-space(@root) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'id' tag 'root' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@extension"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'id' tag 'extension' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@extension) or normalize-space(@extension) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / State Authority Number" -
                The 'id' tag 'extension' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:act/cda:code/@code = '103.10141']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'RSON'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'RSON'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:act) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.10141']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'INFRM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag 'classCode' attribute shall contain the value 'INFRM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'RQO'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'act' tag 'moodCode' attribute shall contain the value 'RQO'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:text"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'text' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:text) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'text' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="not(cda:text) or normalize-space(cda:text) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'text' tag shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="cda:code[@code = '103.10141']">

            <assert test="
                count(/cda:ClinicalDocument//cda:code[@code = '103.10141']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:act/cda:code[@code='103.10141'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/act/code[@code = '103.10141']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Reason for Therapeutic Good'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'code' tag 'displayName' attribute shall contain the value 'Reason for Therapeutic Good'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.10141']/cda:text">

            <report test="@xsi:type and normalize-space(@xsi:type) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'text' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="
                @xsi:type and normalize-space(@xsi:type) != '' and 
                normalize-space(@xsi:type) != 'ST' and
                not(substring-before(normalize-space(@xsi:type), ':') != '' and
                    substring-after(normalize-space(@xsi:type), ':') = 'ST')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Reason for Therapeutic Good" -
                The 'text' tag 'xsi:type' attribute shall contain the value 'ST'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship[cda:act/cda:code/@code = '103.16044']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'entryRelationship' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'COMP'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'entryRelationship' tag 'typeCode' attribute shall contain the value 'COMP'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:act) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.16044']">

            <assert test="@classCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag 'classCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@classCode) or normalize-space(@classCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@classCode) or normalize-space(@classCode) = '' or @classCode = 'INFRM'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag 'classCode' attribute shall contain the value 'INFRM'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'act' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:text"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'text' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:text) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'text' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="not(cda:text) or normalize-space(cda:text) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'text' tag shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="cda:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="cda:code[@code = '103.16044']">

            <assert test="
                count(/cda:ClinicalDocument//cda:code[@code = '103.16044']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:entryRelationship/cda:act/cda:code[@code='103.16044'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/entryRelationship/act/code[@code = '103.16044']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Additional Comments'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'code' tag 'displayName' attribute shall contain the value 'Additional Comments'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:entryRelationship/cda:act[cda:code/@code = '103.16044']/cda:text">

            <report test="@xsi:type and normalize-space(@xsi:type) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'text' tag 'xsi:type' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="
                @xsi:type and normalize-space(@xsi:type) != '' and 
                normalize-space(@xsi:type) != 'ST' and
                not(substring-before(normalize-space(@xsi:type), ':') != '' and
                    substring-after(normalize-space(@xsi:type), ':') = 'ST')"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Additional Comments" -
                The 'text' tag 'xsi:type' attribute shall contain the value 'ST'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:reference[cda:externalAct/cda:code/@code = '103.16444']">

            <assert test="@typeCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'reference' tag 'typeCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@typeCode) or normalize-space(@typeCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'reference' tag 'typeCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@typeCode) or normalize-space(@typeCode) = '' or @typeCode = 'SPRT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'reference' tag 'typeCode' attribute shall contain the value 'SPRT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="cda:seperatableInd"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'seperatableInd' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:seperatableInd) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'seperatableInd' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="count(cda:externalAct) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'externalAct' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:reference[cda:externalAct/cda:code/@code = '103.16444']/cda:seperatableInd">

            <assert test="@value"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'seperatableInd' tag 'value' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@value) or normalize-space(@value) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'seperatableInd' tag 'value' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@value) or normalize-space(@value) = '' or @value = 'true'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'seperatableInd' tag 'value' attribute shall contain the value 'true'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16211']/cda:entry/cda:substanceAdministration[@classCode = 'SBADM']/cda:reference/cda:externalAct[cda:code/@code = '103.16444']">

            <report test="@classCode and normalize-space(@classCode) = ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'externalAct' tag 'classCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <report test="
                @classCode and normalize-space(@classCode) != '' and @classCode != 'ACT'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'externalAct' tag 'classCode' attribute shall contain the value 'ACT'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="@moodCode"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'externalAct' tag 'moodCode' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(@moodCode) or normalize-space(@moodCode) != ''"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'externalAct' tag 'moodCode' attribute shall contain a value.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@moodCode) or normalize-space(@moodCode) = '' or @moodCode = 'EVN'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'externalAct' tag 'moodCode' attribute shall contain the value 'EVN'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:code) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

            <assert test="cda:id"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'id' tag is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <report test="count(cda:id) > 1"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'id' tag shall appear only once.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</report>

        </rule>


        <rule context="cda:code[@code = '103.16444']">

            <assert test="
                count(/cda:ClinicalDocument//cda:code[@code = '103.16444']) =
                count(/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='102.16211']/cda:entry/cda:substanceAdministration[@classCode='SBADM']/cda:reference/cda:externalAct/cda:code[@code='103.16444'])"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The '/ClinicalDocument/component/structuredBody/component/section[code/@code = '102.16211']/entry/substanceAdministration[@classCode = 'SBADM']/reference/externalAct/code[@code = '103.16444']' path -
                1 or more tags are missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystem"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag 'codeSystem' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystem) or normalize-space(@codeSystem) = '' or @codeSystem = '1.2.36.1.2001.1001.101'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag 'codeSystem' attribute shall contain the value '1.2.36.1.2001.1001.101'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@codeSystemName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag 'codeSystemName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@codeSystemName) or normalize-space(@codeSystemName) = '' or @codeSystemName = 'NCTIS Data Components'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag 'codeSystemName' attribute shall contain the value 'NCTIS Data Components'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="@displayName"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag 'displayName' attribute is missing.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="
                not(@displayName) or normalize-space(@displayName) = '' or @displayName = 'Medication Instruction Identifier'"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'code' tag 'displayName' attribute shall contain the value 'Medication Instruction Identifier'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

            <assert test="not(cda:translation)"
                >Error: e-Prescription - 8.2 Prescription item -
                "Prescription Item / Medication Instruction Identifier" -
                The 'translation' tag shall not be present within 'code'.
                Refer to section 8.2 of the
                e-Prescription_CDA_Implementation_Guide_v0.3.</assert>

        </rule>


    </pattern>

</schema>
