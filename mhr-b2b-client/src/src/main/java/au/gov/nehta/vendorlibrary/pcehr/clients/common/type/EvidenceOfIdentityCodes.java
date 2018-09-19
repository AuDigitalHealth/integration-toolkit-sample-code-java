/*
 * Copyright 2012 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.pcehr.clients.common.type;

public enum EvidenceOfIdentityCodes {
	//Todo:replace with meanignful code names
	IdentityVerificationMethod1("IdentityVerificationMethod1"),
	IdentityVerificationMethod2("IdentityVerificationMethod2"),
	IdentityVerificationMethod3("IdentityVerificationMethod3"),
	IdentityVerificationMethod4("IdentityVerificationMethod4"),
	IdentityVerificationMethod5("IdentityVerificationMethod5"),
	IdentityVerificationMethod6("IdentityVerificationMethod6"),
	IdentityVerificationMethod7("IdentityVerificationMethod7"),
	IdentityVerificationMethod8("IdentityVerificationMethod8"),
	IdentityVerificationMethod9("IdentityVerificationMethod9"),
	IdentityVerificationMethod10("IdentityVerificationMethod10");


	private String code;
	private EvidenceOfIdentityCodes(String code){
		this.code= code;
	}
	public String getCode() {
		return code;
	}
	
}
