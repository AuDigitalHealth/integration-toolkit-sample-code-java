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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.exception;

/**
 * Thrown to indicate that signature validation has failed.
 */
public class SignatureValidationException extends RuntimeException {

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message Detail message.
   */
  public SignatureValidationException(final String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message the detail message.
   * @param e       the original cause of the exception. Null is allowed when the cause is unknown.
   */
  public SignatureValidationException(final String message, final Exception e) {
    super(message, e);
  }
}

