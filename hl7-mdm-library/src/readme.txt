====================================
Introduction
====================================

This is a software utility library for creating MDM messages, encapsulating
CDA package file content.

====================================
Setup
====================================

-   To build and test the distributable package, an appropriate Java IDE or
    build environment must be installed.

-   For detailed API documentation, refer to the included Javadoc packages.

====================================
Solution
====================================

The solution consists of the following packages:

    -   au.gov.nehta.vendorlibrary.mdm.core
        Contains the message model.

    -   au.gov.nehta.vendorlibrary.mdm.segments
        Contains the logical segment classes used in building up a message.

    -   au.gov.nehta.vendorlibrary.mdm.enums
        Enumerators used by this vendor library

    -   au.gov.nehta.vendorlibrary.mdm.util
        Contains utility classes for helper class functionality.

====================================
Examples
====================================

Example code is included showing MDM message creation/extraction:

    -   /src/sample/java/au/gov/nehta/vendorlibrary/mdm/sample/MDMGenerateSample.java

    -   /src/sample/java/au/gov/nehta/vendorlibrary/mdm/sample/MDMExtractSample.java

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
    directory in [2].

4.  Add <JDK_HOME>/bin to the system path.

BouncyCastle
------------------------------------
An open source, third-party library has been used in this project for the
purposes of Base 64 encode/decode functionality. More information on this
library and its associated library is available here:

    URL: http://bouncycastle.org

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