<?xml version = "1.0" encoding = "UTF-8"?>

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix="cda" uri="urn:hl7-org:v3"/>
    <ns prefix="ext" uri="http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
    <ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance"/>


    <pattern name="p-e-Referral_1A_CDA_Narrative_Only-errors"
        id="p-e-Referral_1A_CDA_Narrative_Only-errors"
        see="#p-e-Referral_1A_CDA_Narrative_Only-errors">



        <rule context="/cda:ClinicalDocument">

            <assert test="cda:component">Error: e-Referral - The 'component' tag is missing.
                Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</assert>

            <report test="count(cda:component) > 1">Error: e-Referral - The 'component' tag
                shall appear only once. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component">

            <assert test="cda:structuredBody">Error: e-Referral - The 'structuredBody' tag is
                missing. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</assert>

            <report test="count(cda:structuredBody) > 1">Error: e-Referral - The
                'structuredBody' tag shall appear only once. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody">


            <assert test="cda:component">Error: e-Referral - The 'component' tag is missing.
                Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</assert>

            <report test="count(cda:component) > 1">Error: e-Referral - The 'component' tag
                shall appear only once. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>

        </rule>


        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component">


            <assert test="cda:section">Error: e-Referral - The 'section' tag is missing.
                Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</assert>
            
            <report test="count(cda:section) > 1">Error: e-Referral - The 'section' tag
                shall appear only once. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>
            
        </rule>



        <rule
            context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section">


            <assert test="cda:title">Error: e-Referral - The 'title' tag is missing. Refer to
                section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</assert>


            <report test="count(cda:title) > 1">Error: e-Referral - The 'title' tag shall
                appear only once. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>
            
            
            <report test="cda:title and normalize-space(cda:title) = ''">Error: e-Referral - The
                'title' tag shall contain a value. Refer to
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>


            <assert test="cda:text">Error: e-Referral - The 'text' tag is missing. Refer to
                section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</assert>


            <report test="count(cda:text) > 1">Error: e-Referral - The 'text' tag shall
                appear only once. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>
                       
            
            <!-- catch any NON-renderMultiMedia elements -->
            <report test="cda:text/cda:content     or
                cda:text/cda:list        or
                cda:text/cda:table       or
                cda:text/cda:br          or
                cda:text/cda:footnote    or
                cda:text/cda:footnoteRef or
                cda:text/cda:linkHtml    or
                cda:text/cda:paragraph   or
                cda:text/cda:sub         or
                cda:text/cda:sup"
                >Error: e-Referral - The non-attachment reference tags shall not be present
                within narrative 'text' block. With level 1A conformance a clinical document shall
                consist of a CDA body that only includes attachment references in the narrative
                block. Refer to section 3.4.2.1 of the
                Common_Conformance_Profile_for_Clinical_Documents_v1.3.</report>
            
        </rule>

    </pattern>

</schema>
