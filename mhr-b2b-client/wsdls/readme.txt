====================================
Introduction
====================================

This library provides the required artefacts required to develop, deploy
and invoke the relevant PCEHR web services.

====================================
Setup
====================================

-   To build and test the distributable package, an appropriate Java IDE or
    build environment must be installed.

-   WSDL/XSD source files should be used in conjunction with JAX-WS and wsimport
    to build the generated Java classes/source files. These WSDL/XSD files can be
    found at:
    /src/main/resources/*

    Generated Java source files can be found in:
    /api/nehta-vendorlibrary-java-pcehr-compiled-wsdl-<version>-sources.jar

-   For detailed API documentation, refer to the included Javadoc package.

====================================
Solution
====================================

The package consists of these components:

    -   /api/nehta-vendorlibrary-java-pcehr-compiled-wsdl-<version>.jar
        Contains the required classes for doesPCEHRExist web service
        development,deployment and invocation.

    -   /api/nehta-vendorlibrary-java-pcehr-compiled-wsdl-<version>-docs.jar
        Contains Javadoc for generated code.

    -   /api/nehta-vendorlibrary-java-pcehr-compiled-wsdl-<version>-sources.jar
        Contains artefact Java and WSDL/XSD source files.

    -   /lib/provided/*.jar
        Contains the provided libraries necessary to generate the web service
        code from the WSDL/XSD files.

====================================
Pre-Requisites
====================================

Java Development Kit (JDK)
------------------------------------
1.  Download and install JDK 6 Update 27 or later:
    URL: http://www.oracle.com/technetwork/java/javase/downloads/index.html

2.  Unpack the JDK distribution into a directory of your choice.

    This directory will be your <JDK_HOME>and will be used in this document
    to refer to the root directory of the JDK installation.

    <JRE_HOME> will be used in this document to refer to <JDK_HOME>/jre.

3.  Create a JAVA_HOME environment variable pointing to the <JDK_HOME>
    directory in Step 2.

4.  Add <JDK_HOME>/bin to the system path.


JAX-WS 2.2.5
------------------------------------
An open source, third-party library has been used in this project for the
purposes generating all required classes for web service development,
deployment and invocation. Further information on this library can be found
at:

    URL: http://jax-ws.java.net/2.2.5/

====================================
Licensing
====================================
Copyright 2012 NEHTA

Licensed under the NEHTA Open Source (Apache) License; you may not use this
file except in compliance with the License. A copy of the License is in the
'license.txt' file, which should be provided with this work.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.
