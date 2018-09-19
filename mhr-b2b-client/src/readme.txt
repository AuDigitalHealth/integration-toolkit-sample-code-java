====================================
Introduction
====================================

This library provides an example client that invokes various PCEHR web service operations
as defined in the appropriate Logical/Technical Service Specifications.

====================================
Setup
====================================

-   To build and test the distributable package, an appropriate Java IDE or
    build environment must be installed.

-   Java source files can be found in:
    /api/nehta-vendorlibrary-java-pcehrclient-<version>-sources.jar

-   For detailed API documentation, refer to the included Javadoc package.

====================================
Solution
====================================

The package consists of these components:

    -   /api/nehta-vendorlibrary-java-pcehrclient-<version>.jar
        Contains the required classes for PCEHR web service invocations.

    -   /api/nehta-vendorlibrary-java-pcehrclient-<version>-docs.jar
        Contains Javadoc documentation.

    -   /api/nehta-vendorlibrary-java-pcehrclient-<version>-sources.jar
        Contains PCEHR web services client source.

    -   /lib/provided/*.jar
        Contains the provided libraries necessary to support the webs service
        client.

====================================
Pre-Requisites
====================================

Java Development Kit (JDK)
------------------------------------
1.  Download and install JDK 7 Update 15 or later:
    URL: http://www.oracle.com/technetwork/java/javase/downloads/index.html

2.  Unpack the JDK distribution into a directory of your choice.

    This directory will be your <JDK_HOME>and will be used in this document
    to refer to the root directory of the JDK installation.

    <JRE_HOME> will be used in this document to refer to <JDK_HOME>/jre.

3.  Create a JAVA_HOME environment variable pointing to the <JDK_HOME>
    directory in Step 2.

4.  Add <JDK_HOME>/bin to the system path.

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