<?xml version = "1.0" encoding = "UTF-8"?>
<!--
_________________________________________________________________________________________________________________________________________________________________________________________
                  Generated File - Do Not Edit File Directly                                                                                                                             
                  __________________________________________

                  Generated          : 31/03/2015 11:20:18 AM

                  Product            : Pathology Report
                  Release            : CDA_Implementation_Guide_v1.0
                  Generator Version  : 
                                                                                                                                                                                         
_________________________________________________________________________________________________________________________________________________________________________________________
-->

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix = "cda" uri = "urn:hl7-org:v3"/>
    <ns prefix = "ext" uri = "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix = "xs" uri = "http://www.w3.org/2001/XMLSchema"/>
    <ns prefix = "xsi" uri = "http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-Pathology_Report_Entity_Identifier_Checks-errors"
        id="p-Pathology_Report_Entity_Identifier_Checks-errors"
        see="#p-Pathology_Report_Entity_Identifier_Checks-errors">



        <rule context="ext:asEntityIdentifier/ext:id">


            <report
                test="(starts-with(@root, '1.2.36.1.2001.1003.0.800360')) and (@assigningAuthorityName) and (@assigningAuthorityName!='IHI')"
                >Error: Pathology Report - Global Clinical Document check for
                "ext:asEntityIdentifier/ext:id" tag - The 'ext:id' tag 'assigningAuthorityName'
                attribute shall contain a value 'IHI' if its 'root' attribute starts with
                '1.2.36.1.2001.1003.0.800360'. Check all 'ext:asEntityIdentifier/ext:id' tag to find
                the missing or incorrect attribute value. Refer to section 8.4 Entity Identifier of
                the Pathology_Report_CDA_Implementation_Guide_v1.0.</report>

            <report
                test="(starts-with(@root, '1.2.36.1.2001.1003.0.800361')) and (@assigningAuthorityName) and (@assigningAuthorityName!='HPI-I')"
                >Error: Pathology Report - Global Clinical Document check for
                "ext:asEntityIdentifier/ext:id" tag - The 'ext:id' tag 'assigningAuthorityName'
                attribute shall contain a value 'HPI-I' if its 'root' attribute starts with
                '1.2.36.1.2001.1003.0.800361'. Check all 'ext:asEntityIdentifier/ext:id' tag to find
                the missing or incorrect attribute value. Refer to section 8.4 Entity Identifier of
                the Pathology_Report_CDA_Implementation_Guide_v1.0.</report>

            <report
                test="(starts-with(@root, '1.2.36.1.2001.1003.0.800362')) and (@assigningAuthorityName) and (@assigningAuthorityName!='HPI-O')"
                >Error: Pathology Report - Global Clinical Document check for
                "ext:asEntityIdentifier/ext:id" tag - The 'ext:id' tag 'assigningAuthorityName'
                attribute shall contain a value 'HPI-O' if its 'root' attribute starts with
                '1.2.36.1.2001.1003.0.800362'. Check all 'ext:asEntityIdentifier/ext:id' tag to find
                the missing or incorrect attribute value. Refer to section 8.4 Entity Identifier of
                the Pathology_Report_CDA_Implementation_Guide_v1.0.</report>

            
           <report
                test="(starts-with(@root, '1.2.36.1.2001.1007.1.800364')) and (@assigningAuthorityName) and (@assigningAuthorityName!='PAI-O')"
                >Error: Pathology Report - Global Clinical Document check for
                "ext:asEntityIdentifier/ext:id" tag - The 'ext:id' tag 'assigningAuthorityName'
                attribute shall contain a value 'PAI-O' if its 'root' attribute starts with
                '1.2.36.1.2001.1007.1.800364'. Check all 'ext:asEntityIdentifier/ext:id' tag to find
                the missing or incorrect attribute value. Refer to section 8.4 Entity Identifier of
                the Pathology_Report_CDA_Implementation_Guide_v1.0.</report>

            <report
                test="(starts-with(@root, '1.2.36.1.2001.1007.10.800364')) and (@assigningAuthorityName) and (@assigningAuthorityName!='PAI-R')"
                >Error: Pathology Report - Global Clinical Document check for
                "ext:asEntityIdentifier/ext:id" tag - The 'ext:id' tag 'assigningAuthorityName'
                attribute shall contain a value 'PAI-R' if its 'root' attribute starts with
                '1.2.36.1.2001.1007.10.800364'. Check all 'ext:asEntityIdentifier/ext:id' tag to
                find the missing or incorrect attribute value. Refer to section 8.4 Entity
                Identifier of the Pathology_Report_CDA_Implementation_Guide_v1.0.</report>

            <report
                test="(starts-with(@root, '1.2.36.1.2001.1007.20.800364')) and (@assigningAuthorityName) and (@assigningAuthorityName!='PAI-D')"
                >Error: Pathology Report - Global Clinical Document check for
                "ext:asEntityIdentifier/ext:id" tag - The 'ext:id' tag 'assigningAuthorityName'
                attribute shall contain a value 'PAI-D' if its 'root' attribute starts with
                '1.2.36.1.2001.1007.20.800364'. Check all 'ext:asEntityIdentifier/ext:id' tag to find
                the missing or incorrect attribute value. Refer to section 8.4 Entity Identifier of
                the Pathology_Report_CDA_Implementation_Guide_v1.0.</report>

        </rule>

    

    </pattern>

</schema>
