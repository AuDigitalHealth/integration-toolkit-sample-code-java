<?xml version = "1.0" encoding = "UTF-8"?>

<schema xmlns="http://www.ascc.net/xml/schematron">
    <ns prefix="cda" uri="urn:hl7-org:v3"/>
    <ns prefix="ext" uri="http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"/>
    <ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
    <ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance"/>

    <pattern name="p-e-Referral_3A_Global_Code_Checks_custom-errors"
        id="p-e-Referral_3A_Global_Code_Checks_custom-errors" 
        see="#p-e-Referral_3A_Global_Code_Checks_custom-errors">


        <!-- 6.1.2 SUBJECT OF CARE - Date of Birth is Calculated From Age -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16080']/cda:entry/cda:observation/cda:code[@displayName = 'Date of Birth is Calculated From Age']">
            
            <assert
                test="@code = '103.16233'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16233'. Refer to section '6.1.2 SUBJECT OF CARE' of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
  
        
        <!-- 6.1.2 SUBJECT OF CARE -  DATE OF BIRTH ACCURACY INDICATOR  -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16080']/cda:entry/cda:observation/cda:code[@displayName = 'Date of Birth Accuracy Indicator']">
            
            <assert
                test="@code = '102.16234'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '102.16234'. Refer to section 6.1.2 SUBJECT OF CARE of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
 
        </rule>
        
        
        
        <!-- 6.1.2 SUBJECT OF CARE - AGE-->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16080']/cda:entry/cda:observation/cda:code[@displayName ='Age']">
            
            <assert
                test="@code = '103.20109'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.20109'. Refer to section 6.1.2 SUBJECT OF CARE of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule>
        
        <!-- 6.1.2 SUBJECT OF CARE - Age Accuracy Indicator-->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16080']/cda:entry/cda:observation/cda:code[@displayName = 'Age Accuracy Indicator']">
            
            <assert
                test="@code = '103.16279'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16279'. Refer to section 6.1.2 SUBJECT OF CARE of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>
        
        <!-- 6.1.2 SUBJECT OF CARE - Birth Plurality -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16080']/cda:entry/cda:observation/cda:code[@displayName = 'Birth Plurality']">
            
            <assert
                test="@code = '103.16249'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16249'. Refer to section 6.1.2 SUBJECT OF CARE of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
         
        </rule>
        
        <!-- 6.1.2 SUBJECT OF CARE - Date of Death Accuracy Indicator -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16080']/cda:entry/cda:observation/cda:code[@displayName = 'Date of Death Accuracy Indicator']">
            
            <assert
                test="@code = '102.16252'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '102.16252'. Refer to section 6.1.2 SUBJECT OF CARE of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
        
        </rule>


        <!-- 7.1.1 REFERRAL DETAIL - Referral DateTime -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16347']/cda:entry/cda:observation/cda:code[@displayName = 'Referral DateTime']">
            
            <assert
                test="@code = '103.16620'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16620'. Refer to section 7.1.1 Referral Detail of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>
        
        <!-- 7.1.1 REFERRAL DETAIL - Referral Reason -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16347']/cda:entry/cda:observation/cda:code[@displayName = 'Reason for referral']">
            
            <assert
                test="@code = '42349-1'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '42349-1'. Refer to section 7.1.1 Referral Detail of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
  
        </rule>
        
        
        <!-- 7.1.1 REFERRAL DETAIL - Referral Validity Duration -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '102.16347']/cda:entry/cda:observation/cda:code[@displayName = 'Referral Validity Duration']">
            
            <assert
                test="@code = '103.16622'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16622'. Refer to section 7.1.1 Referral Detail of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>


        <!-- 7.1.2.1 PROBLEM/DIAGNOSIS - Problem/Diagnosis -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.16117']/cda:entry/cda:observation/cda:code[@displayName = 'Diagnosis interpretation']">
            
            <assert
                test="@code = '282291009'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '282291009'. Refer to section 7.1.2.1 PROBLEM/DIAGNOSIS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>


        <!-- 7.1.2.1 PROBLEM/DIAGNOSIS - Date of Resolution/Remission -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.16117']/cda:entry/cda:observation[cda:code/@code = '282291009']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Date of Resolution/Remission']">
            
            <assert test="@code = '103.15510'">Error: e-Referral - Global Code Checks -
                The 'code' tag 'code' attribute shall contain the value '103.15510''. Refer to section 7.1.2.1
                PROBLEM/DIAGNOSIS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule>
        
        <!-- 7.1.2.1 PROBLEM/DIAGNOSIS - Problem/Diagnosis Comment -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.16117']/cda:entry/cda:observation[cda:code/@code='282291009']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Problem/Diagnosis Comment']">
            
            <assert test="@code = '103.16545'">Error: e-Referral - Global Code Checks -
                The 'code' tag 'code' attribute shall contain the value '103.16545'. Refer to section 7.1.2.1 PROBLEM/DIAGNOSIS
                of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule> 
        
        
        <!-- 7.1.2.2 PROCEDURE - Procedure Comment -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.16117']/cda:entry/cda:procedure[@classCode='PROC']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Procedure Comments']">
            
            <assert test="@code = '103.15595'">Error: e-Referral - Global Code Checks -
                The 'code' tag 'code' attribute shall contain the value '103.15595'. Refer to section 7.1.2.2
                PROCEDURE of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>   
       
        <!-- 7.1.2.3 OTHER MEDICAL HISTORY ITEM - Other Medical History Item -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.16117']/cda:entry/cda:act/cda:code[@displayName = 'Other Medical History Item']">    

            <assert test="@code = '102.16627'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '102.16627'. Refer to section 7.1.2.3 OTHER MEDICAL HISTORY ITEM of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
  
       </rule>
        
        <!-- 7.1.2.3 OTHER MEDICAL HISTORY ITEM - Medical History Item Comment -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.16117']/cda:entry/cda:act[cda:code/@code = '102.16627']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Medical History Item Comment']">       
            
            <assert test="@code = '103.16630'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16630'. Refer to section 7.1.2.3 OTHER MEDICAL HISTORY ITEM of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>

        </rule>
        
        
        <!-- 7.1.3.2 EXCLUSION STATEMENT - MEDICATIONS -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='101.16146']/cda:entry/cda:observation/cda:code[@displayName = 'Global Statement']">
            
            <assert
                test="@code = '103.16302.2.2.1'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16302.2.2.1'. Refer to section 7.1.3.2 EXCLUSION STATEMENT - MEDICATIONS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule>      
        
        <!-- 7.1.4.1 ADVERSE REACTION - Adverse Reaction -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20113']/cda:entry/cda:act/cda:code[@displayName = 'Adverse Reaction']">
          
            <assert test="@code = '102.15517'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '102.15517'. Refer to section 7.1.4.1 ADVERSE REACTION of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
            </rule>
                
        <!-- 7.1.4.1 ADVERSE REACTION - Reaction Event -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20113']/cda:entry/cda:act[cda:code/@code = '102.15517']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Reaction Event']">
            
            <assert test="@code = '102.16474'"
                >Error: e-Referral - Global Code Checks -
                The 'code' tag 'code' attribute shall contain the value '102.16474''. Refer to section 7.1.4.1
                ADVERSE SUBSTANCE REACTION of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>            

        <!-- 7.1.4.2 EXCLUSION STATEMENT - ADVERSE REACTIONS -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20113']/cda:entry/cda:observation/cda:code[@displayName = 'Global Statement']">
            
            <assert
                test="@code = '103.16302.2.2.2'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16302.2.2.2'. Refer to section 
                7.1.4.2 EXCLUSION STATEMENT - ADVERSE REACTIONS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>

        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Diagnostic Service -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'pathology service']">
            
            <assert test="@code = '310074003'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '310074003'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>
       
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Overall Pathology Test Result Status -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='101.20117']/cda:component/cda:section[cda:code/@code='102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'report status']">
            
            <assert test="@code = '308552006'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '308552006'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
         
        </rule>
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Clinical Information Provided -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Clinical information']">
            
            <assert
                test="@code = '55752-0'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall contain the value
                '55752-0'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule>
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT -Pathological Diagnosis -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'pathology diagnosis']">
            
            <assert test="@code = '88101002'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '88101002'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule>
        
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Pathology Test Conclusion -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'laboratory findings data interpretation']">
            
            <assert test="@code = '386344002'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '386344002'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
      
        </rule>
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT -Test Comment -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Test Comment']">
            
            <assert
                test="@code = '103.16468'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall contain the value
                '103.16468'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
       
        </rule>
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Test Request Details -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Test Request Details']">
            
            <assert
                test="@code = '102.16160'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall contain the value
                '102.16160'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule> 
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Test Requested Name -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16160']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Test Requested Name']">
            
            <assert test="@code = '103.11017'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.11017'. Refer to section 7.1.5.1 PATHOLOGY TEST RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
        <!-- 7.1.5.1 PATHOLOGY TEST RESULT - Pathology Test Result DateTime -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Pathology Test Result DateTime']">
            
            <assert test="@code = '103.16605'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16605'. Refer to section 7.1.5.1 PATHOLOGY TEST of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>   
        
        <!-- 7.1.5.1.1 TEST SPECIMEN DETAIL - Test Specimen Detail -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Test Specimen Detail']">
            
            <assert test="@code = '102.16156.2.2.1'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '102.16156.2.2.1'. Refer to section 7.1.5.1.1 TEST SPECIMEN DETAIL of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
        <!-- 7.1.5.1.1 TEST SPECIMEN DETAIL - Side -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation[cda:code/@code = '102.16156.2.2.1']/cda:targetSiteCode/cda:qualifier/cda:name[@displayName = 'with laterality']">
            
            <assert test="@code = '78615007'">Error: e-Referral - Global Code Checks -
                'name' tag 'code' attribute shall contain the value '78615007'. Refer to section 7.1.5.1.1
                TEST SPECIMEN DETAIL of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
         
        </rule>
        
       
        <!-- 7.1.5.1.1 TEST SPECIMEN DETAIL - Sampling Preconditions -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation[cda:code/@code = '102.16156.2.2.1']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Sampling Preconditions']"> 
            
            <assert test="@code = '103.16171'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.16171'. Refer to section 7.1.5.1.1 TEST SPECIMEN DETAIL of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
         
        </rule>
        
        <!-- 7.1.5.1.1 TEST SPECIMEN DETAIL - Collection Setting -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation[cda:code/@code = '102.16156.2.2.1']/cda:entryRelationship/cda:observation/cda:code[@displayName ='Collection Setting']"> 
            
            <assert test="@code = '103.16529'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.16529'. Refer to section 7.1.5.1.1 TEST SPECIMEN DETAIL of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
      
        </rule>
        
        <!-- 7.1.5.1.1 PATHOLOGY TEST SPECIMEN DETAIL - DateTime Received -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation[cda:code/@code = '102.16156.2.2.1']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'DateTime Received']"> 
            
            <assert test="@code = '103.11014'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.11014'. Refer to section 7.1.5.1.1 TEST SPECIMEN DETAIL of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
        <!-- 7.1.5.1.1 PATHOLOGY TEST SPECIMEN DETAIL - Parent Specimen Identifier -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation[cda:code/@code = '102.16156.2.2.1']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Parent Specimen Identifier']"> 
            
            <assert test="@code = '103.16187'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.16187'. Refer to section 7.1.5.1.1 TEST SPECIMEN DETAIL of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
        
        </rule>
        
        <!-- 7.1.5.1.2 PATHOLOGY TEST RESULT GROUP -  Individual Pathology Test Result Comment -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode = 'OBS']/cda:entryRelationship/cda:organizer[@classCode = 'BATTERY']/cda:component/cda:observation[(@classCode = 'OBS') and (cda:code/@code!='102.16156.2.2.2')]/cda:entryRelationship/cda:act/cda:code[@displayName = 'result comments']">
            
            <assert test="@code = '281296001'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '281296001'. Refer to section 7.1.5.1.2 PATHOLOGY TEST RESULT GROUP of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
        <!-- 7.1.5.1.2 PATHOLOGY TEST RESULT GROUP - Individual Pathology Test Reference Range Guidance -->
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode = 'OBS']/cda:entryRelationship/cda:organizer[@classCode = 'BATTERY']/cda:component/cda:observation[(@classCode = 'OBS') and (cda:code/@code!='102.16156.2.2.2')]/cda:entryRelationship/cda:act/cda:code[@displayName = 'reference range comments']">
            
            <assert test="@code = '281298000'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '281298000'. Refer to section 7.1.5.1.2 PATHOLOGY TEST RESULT GROUP of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
  
        </rule>

        
           <!-- 7.1.5.1.2 PATHOLOGY TEST RESULT GROUP - Individual Pathology Test Result Status -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode = 'OBS']/cda:entryRelationship/cda:organizer[@classCode = 'BATTERY']/cda:component/cda:observation[(@classCode = 'OBS') and (cda:code/@code!='102.16156.2.2.2')]/cda:entryRelationship/cda:observation/cda:code[@displayName = 'report status']">
            
            <assert test="@code = '308552006'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '308552006'. Refer to section 7.1.5.1.2 PATHOLOGY TEST RESULT GROUP of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>

        <!-- 7.1.5.1.2.1 RESULT GROUP SPECIMEN DETAIL - Result Group Specimen Detail -->           
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section  [cda:code/@code='101.20117']/cda:component/cda:section[cda:code/@code='102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation/cda:code[@displayName ='Result Group Specimen Detail']">
            
            
            <assert test="@code = '102.16156.2.2.2'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '102.16156.2.2.2'. Refer to section 
                7.1.5.1.2.1 RESULT GROUP SPECIMEN DETAIL of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
        
        </rule>
   
         <!-- 7.1.5.1.2.1 RESULT GROUP SPECIMEN DETAIL - Side -->
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code='101.20117']/cda:component/cda:section[cda:code/@code='102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation[cda:code/@code='102.16156.2.2.2']/cda:targetSiteCode/cda:qualifier/cda:name[@displayName ='with laterality']">
            
            <assert test="@code = '78615007'">Error: e-Referral - Global Code Checks -
                'name' tag 'code' attribute shall contain the value '78615007'. 
                Refer to section 7.1.5.1.2.1 RESULT GROUP SPECIMEN DETAIL of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>

        </rule>
        
        <!-- 7.1.5.1.2.1 Result Group Specimen Detail - Sampling Preconditions -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Sampling Preconditions']">
            
            <assert test="@code = '103.16171'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.16171'. Refer to section 7.1.5.1.2.1 Result Group Specimen Detail of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
    
        </rule>
        
        <!-- 7.1.5.1.2.1 Result Group Specimen Detail - Collection Setting -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Collection Setting']">
            
            <assert test="@code = '103.16529'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.16529'. Refer to section 7.1.5.1.2.1 Result Group Specimen Detail of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
        
        </rule>
        
        <!-- 7.1.5.1.2.1 Result Group Specimen Detail - DateTime Received -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName ='DateTime Received']">
            
            <assert test="@code = '103.11014'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.11014'. Refer to section 7.1.5.1.2.1 Result Group Specimen Detail of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
         
        </rule>
        
        <!-- 7.1.5.1.2.1 Result Group Specimen Detail - Parent Specimen Identifier -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16144']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Parent Specimen Identifier']">
            
            <assert test="@code = '103.16187'"
                >Error: e-Referral - Global Code Checks - 
                The 'code' tag 'code' attribute shall be '103.16187'. Refer to section 7.1.5.1.2.1 Result Group Specimen Detail of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>
        
        <!-- 7.1.5.2 Imaging Examination Result - Side -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:targetSiteCode/cda:qualifier/cda:name[@displayName = 'with laterality']">
            
            <assert test="@code = '78615007'">Error: e-Referral - Global Code Checks -
                The 'name' tag 'code' attribute shall contain the value '78615007'. Refer to section 7.1.5.2 Imaging Examination Result 
                of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule> 
        
       
         <!-- 7.1.5.2 IMAGING EXAMINATION RESULT - Imaging Examination Result Status -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName ='report status']">
            
            <assert test="@code = '308552006'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '308552006'. Refer to section 7.1.5.2 IMAGING EXAMINATION RESULT
                of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
       
        </rule>
        
        <!-- 7.1.5.2 IMAGING EXAMINATION RESULT - Clinical Information Provided  -->
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Clinical information']">
            
            <assert
                test="@code = '55752-0'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall contain the value
                '55752-0'. Refer to section 7.1.5.2
                IMAGING EXAMINATION RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>
        
        <!-- 7.1.5.2 IMAGING EXAMINATION RESULT - Findings -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Findings']">
            
            <assert test="@code = '103.16503'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16503'. Refer to section 7.1.5.2 IMAGING EXAMINATION RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
        <!-- 7.1.5.2 IMAGING EXAMINATION RESULT - Imaging Examination Result DateTime -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName ='Imaging Examination Result DateTime']">
            
            <assert test="@code = '103.16589'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16589'. Refer to section 7.1.5.2 IMAGING EXAMINATION RESULT of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
        
        </rule>
        
    
            <!-- 7.1.5.2.1 IMAGING EXAMINATION RESULT GROUP -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode='BATTERY']/cda:component/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'result comments']">
            
            <assert test="@code = '281296001'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '281296001'. Refer to section 
                7.1.5.2.1 IMAGING EXAMINATION RESULT GROUP of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
      
            </rule>
        
        <!-- 7.1.5.2.1 IMAGING EXAMINATION RESULT GROUP - Specific Location - Side -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:organizer[@classCode = 'BATTERY']/cda:component/cda:observation[@classCode = 'OBS']/cda:targetSiteCode/cda:qualifier/cda:name[@displayName = 'with laterality']">
            
            <assert test="@code = '78615007'">Error: e-Referral - Global Code Checks -
                'name' tag 'code' attribute shall contain the value '78615007'. 
                Refer to section 7.1.5.2.1 IMAGING EXAMINATION RESULT GROUP of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
    
        </rule>
        
        
        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - Examination Request Details-->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Examination Request Details']">
            
            <assert
                test="@code = '102.16511'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall contain the value
                '102.16511'. Refer to section 7.1.5.2.2 EXAMINATION REQUEST DETAILS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
        </rule>
        
        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - Examination Requested Name -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16511']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Examination Requested Name']">
            
            <assert test="@code = '103.16512'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16512'. Refer to section 7.1.5.2.2 EXAMINATION REQUEST DETAILS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
    
        </rule>
        
        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - DICOM Study Identifier -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16511']/cda:entryRelationship/cda:act/cda:code[@displayName = 'DICOM Study Identifier']">
            
            <assert test="@code = '103.16513'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16513'. Refer to section 7.1.5.2.2 EXAMINATION REQUEST DETAILS of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>

        </rule>
        
        
        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - Report Identifier -->
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16511']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Report Identifier']">
            
            <assert test="@code = '103.16514'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall be
                '103.16514'. Refer to section 7.1.5.2.2 EXAMINATION REQUEST DETAILS of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
           
        </rule>
        
        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - Image Detail -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16511']/cda:entryRelationship/cda:act[cda:code/@code = '103.16513']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Image Details']">
            
            <assert test="@code = '103.16515'">Error: e-Referral - Global Code Checks -
                'code' tag 'code' attribute shall contain the value '103.16515'. Refer to section 7.1.5.2.2
                EXAMINATION REQUEST DETAILS of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
         
        </rule>

        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - DICOM Series Identifier-->  
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16511']/cda:entryRelationship/cda:act[cda:code/@code = '103.16513']/cda:entryRelationship/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act/cda:code[@displayName = 'DICOM Series Identifier']">
            
            <assert test="@code = '103.16517'">Error: e-Referral - Global Code Checks -
                'code' tag 'code' attribute shall contain the value '103.16517'. Refer to
                section 7.1.5.2.2 EXAMINATION REQUEST DETAILS of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
      
        </rule>
 
        <!-- 7.1.5.2.2 EXAMINATION REQUEST DETAILS - Subject Position -->  
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16145']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:act[cda:code/@code = '102.16511']/cda:entryRelationship/cda:act[cda:code/@code = '103.16513']/cda:entryRelationship/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'Subject Position']">
            
            <assert test="@code = '103.16519'">Error: e-Referral - Global Code Checks -
                'code' tag 'code' attribute shall contain the value '103.16519'. Refer to section 7.1.5.2.2
                EXAMINATION REQUEST DETAILS of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
   
        </rule>

        <!-- 7.1.5.3 Requested Service - Subject of Care Instruction Description -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.20158']/cda:entry/cda:act[@classCode='ACT']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Subject of Care Instruction Description']">
            
            <assert test="@code = '103.10146'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall
                be '103.10146'. Refer to section 7.1.5.3 Requested Service of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
          </rule>    
        
        <!-- 7.1.5.3 Requested Service - Requested Service DateTime -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.20158']/cda:entry/cda:act[@classCode='ACT']/cda:entryRelationship/cda:act/cda:code[@displayName = 'Requested Service DateTime']">
            
            <assert test="@code = '103.16635'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall
                be '103.16635. Refer to section 7.1.5.3 Requested Service of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>
            
              </rule>
     
        <!-- 7.1.5.4 FAQ_Diagnostic_Investigations_with_correct_OIDS_rev001 -->
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section/cda:code[@displayName = 'Diagnostic Investigation']">
            
            <assert test="@code = '102.16029'"
                >Error: e-Referral - Global Code Checks - The 'code' tag 'code' attribute shall
                be '102.16029. Refer to FAQ_Diagnostic_Investigations_with_correct_OIDS_rev001 of the
                e-Referral_CDA_Implementation_Guide_v2.2.</assert>

        </rule>
     
        <!-- 7.1.5.4 Newly added Other(pathology and radiology) Test Result section - Diagnostic Investigation Test Result Status -->
        
        <rule context="/cda:ClinicalDocument/cda:component/cda:structuredBody/cda:component/cda:section[cda:code/@code = '101.20117']/cda:component/cda:section[cda:code/@code = '102.16029']/cda:entry/cda:observation[@classCode='OBS']/cda:entryRelationship/cda:observation/cda:code[@displayName = 'report status']">
            
            <assert test="@code = '308552006'"
                >Error: e-Referral - Global Code Checks  - The 'code' tag 'code' attribute shall be
                '308552006'. Refer to FAQ_Diagnostic_Investigations_with_correct_OIDS_rev001 of the e-Referral_CDA_Implementation_Guide_v2.2.</assert>
          
        </rule>
     
    </pattern>

</schema>
