<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 17/08/2015 10:18:17 AM

                  Product            : e-Referral
                  Release            : CDA_Implementation_Guide_v1.0
                  Generator Version  : 
                                                                                                                                                                                         
_________________________________________________________________________________________________________________________________________________________________________________________
-->

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix = "cda" uri = "urn:hl7-org:v3"/>
    <ns prefix = "ext" uri = "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix = "xs" uri = "http://www.w3.org/2001/XMLSchema"/>
    <ns prefix = "xsi" uri = "http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-e-Referral_IHI_Common_Conformance_Requirements_Part2-errors"
        id="p-e-Referral_IHI_Common_Conformance_Requirements_Part2-errors"
        see="#p-e-Referral_IHI_Common_Conformance_Requirements_Part2-errors">


<!-- Refer to section 3.4.10 of the Common Conformance Profile -->

<rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/ext:asEntityIdentifier[not(ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.') or (@root = '1.2.36.1.2001.1003.0') or starts-with(@root, '1.2.36.1.2001.1007')])]/ext:id">

             <assert test="@extension">Error: e-Referral - 6.1.2 SUBJECT OF CARE - The 'ext:id' tag 'extension' 
                 attribute is missing. Refer to "023876 - Local identifier
                 details for a person in an Entity Identifier element" of the
                 Clinical_Documents_Common_Conformance_Profile_v1.6 and
                 "section 6.1.2 SUBJECT OF CARE" of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>

             <assert test="@assigningAuthorityName">Error: e-Referral - 6.1.2 SUBJECT OF CARE - The 'ext:id' tag 
                 'assigningAuthorityName' attribute is missing. Refer to "023876 - Local identifier
                 details for a person in an Entity Identifier element" of the
                 Clinical_Documents_Common_Conformance_Profile_v1.6 and 
                 "section 6.1.2 SUBJECT OF CARE" of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>      

        </rule>





<rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/ext:asEntityIdentifier[not(ext:id[starts-with(@root, '1.2.36.1.2001.1003.0.') or (@root = '1.2.36.1.2001.1003.0') or starts-with(@root, '1.2.36.1.2001.1007')])]">
    
            <assert test="ext:code">Error: e-Referral - 6.1.2 SUBJECT OF CARE - 
                The 'ext:code' tag is missing. Refer to "023876 - Local identifier
                details for a person in an Entity Identifier element" of the
                Clinical_Documents_Common_Conformance_Profile_v1.6 and 
                "section 6.1.2 SUBJECT OF CARE" of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
    
        </rule>


    </pattern>

</schema>
