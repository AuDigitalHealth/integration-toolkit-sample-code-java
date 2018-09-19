==============
README.txt
==============
If compiling with <= JDK 1.6 you must include the following files
in the {java_home}\jre\lib\endorsed\ dir:

\lib\provided\  
jaxb-api.jar
jaxb-impl.jar



this is because the JAXB impl in jdk 1.6 has trouble with <xsd:choice>
JDKs > 1.6 don't appear to have any problem. The provided jars are from 
JAXB 2.2.6 and work with <xsd:choice> tags


/extern/xsd is a reference to the set of XSDs that the csharp projects will
use. Java needs to maintain it's own version of these as the imports contain 
circular references. These are in /lib/xsd_java_mod


Error
=====
If you get an error complaining about external schema access

create file: C:\Program Files (x86)\Java\jdk1.8.X_X\jre\lib

jaxp.properties
===============
javax.xml.accessExternalSchema = all