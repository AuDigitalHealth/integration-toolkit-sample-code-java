<?xml version = "1.0" encoding = "UTF-8"?>


<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix="cda" uri="urn:hl7-org:v3"/>
    <ns prefix="ext" uri="http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
    <ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-Shared_Health_Summary_HPI-I_Common_Conformance_Requirements_Part2-errors"
        id="p-Shared_Health_Summary_HPI-I_Common_Conformance_Requirements_Part2-errors"
        see="#p-Shared_Health_Summary_HPI-I_Common_Conformance_Requirements_Part2-errors">


        <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity/cda:assignedPerson/ext:asEntityIdentifier/ext:id[starts-with(@root, '1.2.36.1.2001.1005.41.') or (@root = '1.2.36.1.2001.1005.41')] |
            /cda:ClinicalDocument/cda:author/cda:assignedAuthor/cda:assignedPerson/ext:asEntityIdentifier/ext:id[starts-with(@root, '1.2.36.1.2001.1005.41.') or (@root = '1.2.36.1.2001.1005.41')]">
           
            <assert test="not(@root) or (starts-with(@root, '1.2.36.1.2001.1005.41.800362'))">Error:
                Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute SHALL be the
                OID 1.2.36.1.2001.1005.41 followed by the national healthcare identifier of the
                organisation that maintains the local identifier. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal
                Authenticator and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <report
                test="(@root) and (starts-with(@root, '1.2.36.1.2001.1005.41.800362')) and not(string-length(@root) &gt;= 38)"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute SHALL be the
                OID 1.2.36.1.2001.1005.41 followed by the national healthcare identifier of the
                organisation that maintains the local identifier. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal
                Authenticator and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report
                test="(@root) and (starts-with(@root, '1.2.36.1.2001.1005.41.800362')) and (string-length(@root) &gt;= 38) and contains(substring(@root, 23, 16), '.')"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute SHALL be the
                OID 1.2.36.1.2001.1005.41 followed by the national healthcare identifier of the
                organisation that maintains the local identifier. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal
                Authenticator and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="(@root) and (substring(@root, string-length(@root), 1) = '.')">Error:
                Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute SHALL be the
                OID 1.2.36.1.2001.1005.41 followed by the national healthcare identifier of the
                organisation that maintains the local identifier and SHALL NOT end with a dot (.).
                Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal Authenticator and section
                6.1.2 Document Author of the Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report
                test="(@root) and (starts-with(@root, '1.2.36.1.2001.1005.41.800362')) and (string-length(@root) &gt; 38) and substring(@root, 39, 1) != '.'"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute SHALL be the
                OID 1.2.36.1.2001.1005.41 followed by the national healthcare identifier of the
                organisation that maintains the local identifier. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal
                Authenticator and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report test="(@root) and (number(translate(@root, '0123456789.', '00000000000')) != 0)"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute SHALL be a
                valid OID. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal Authenticator and section
                6.1.2 Document Author of the Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

            <report
                test="(@root) and (starts-with(@root, '1.2.36.1.2001.1005.41.800362')) and not(
                (
                number(substring(string(2 * 8), 1, 1)) + number(substring(string(2 * 8), 2, 1)) +
                0 +
                2 * 0 +
                3 +
                number(substring(string(2 * 6), 1, 1)) + number(substring(string(2 * 6), 2, 1)) +
                number(substring(substring-after(@root, '80036'), 1, 1)) +
                number(substring(string(2 * substring(substring-after(@root, '80036'), 2, 1)), string-length(string(2 * substring(substring-after(@root, '80036'), 2, 1))), 1)) +
                number(substring(string(number(substring(substring-after(@root, '80036'), 2, 1)) div 5), 1, 1)) +
                number(substring(substring-after(@root, '80036'), 3, 1)) +
                number(substring(string(2 * substring(substring-after(@root, '80036'), 4, 1)), string-length(string(2 * substring(substring-after(@root, '80036'), 4, 1))), 1)) +
                number(substring(string(number(substring(substring-after(@root, '80036'), 4, 1)) div 5), 1, 1)) +
                number(substring(substring-after(@root, '80036'), 5, 1)) +
                number(substring(string(2 * substring(substring-after(@root, '80036'), 6, 1)), string-length(string(2 * substring(substring-after(@root, '80036'), 6, 1))), 1)) +
                number(substring(string(number(substring(substring-after(@root, '80036'), 6, 1)) div 5), 1, 1)) +
                number(substring(substring-after(@root, '80036'), 7, 1)) +
                number(substring(string(2 * substring(substring-after(@root, '80036'), 8, 1)), string-length(string(2 * substring(substring-after(@root, '80036'), 8, 1))), 1)) +
                number(substring(string(number(substring(substring-after(@root, '80036'), 8, 1)) div 5), 1, 1)) +
                number(substring(substring-after(@root, '80036'), 9, 1)) +
                number(substring(string(2 * substring(substring-after(@root, '80036'),10, 1)), string-length(string(2 * substring(substring-after(@root, '80036'),10, 1))), 1)) +
                number(substring(string(number(substring(substring-after(@root, '80036'),10, 1)) div 5), 1, 1)) +
                number(substring(substring-after(@root, '80036'), 11, 1))
                ) mod 10 = 0)"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:id' tag 'root' attribute having the
                OID 1.2.36.1.2001.1005.41 followed by the national healthcare identifier of the
                organisation that maintains the local identifier SHALL have a valid Luhn check
                digit. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal Authenticator and section
                6.1.2 Document Author of the Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity/cda:assignedPerson/ext:asEntityIdentifier[not(ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.') or (@root = '1.2.36.1.2001.1003.0')])]/ext:code |
            /cda:ClinicalDocument/cda:author/cda:assignedAuthor/cda:assignedPerson/ext:asEntityIdentifier[not(ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.') or (@root = '1.2.36.1.2001.1003.0')])]/ext:code">
           
            <assert test="@code">Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2
                Document Author - The 'ext:code' tag
                'code' attribute is missing. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal Authenticator and
                section 6.1.2 Document Author of the Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="@codeSystem">Error: Shared Health Summary - 5.1.1 Legal Authenticator and
                6.1.2 Document Author - The 'ext:code' tag
                'codeSystem' attribute is missing. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal Authenticator
                and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert
                test="not(@codeSystem) or normalize-space(@codeSystem) = '' or (@codeSystem='2.16.840.1.113883.12.203')"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:code' tag 'codeSystem' attribute
                shall contain the value '2.16.840.1.113883.12.203'. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal
                Authenticator and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert test="@codeSystemName">Error: Shared Health Summary - 5.1.1 Legal Authenticator and
                6.1.2 Document Author - The 'ext:code' tag
                'codeSystemName' attribute is missing. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal Authenticator
                and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

            <assert
                test="not(@codeSystemName) or normalize-space(@codeSystemName) = '' or (@codeSystemName='Identifier Type (HL7)')"
                >Error: Shared Health Summary - 5.1.1 Legal Authenticator and 6.1.2 Document Author - The 'ext:code' tag 'codeSystemName' attribute
                shall contain the value 'Identifier Type (HL7)'. Refer to 023876 Local identifier details for a person in an Entity
                Identifier element of COMMON-ConformProfileClinDoc-v1.6 and section 5.1.1 Legal
                Authenticator and section 6.1.2 Document Author of the
                Shared_Health_Summary_CDA_Implementation_Guide_v1.4.</assert>

        </rule>

    </pattern>

</schema>
