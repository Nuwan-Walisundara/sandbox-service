/*******************************************************************************
 * Copyright  (c) 2015-2016, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 * 
 * WSO2.Telco Inc. licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.wso2telco.services.dep.sandbox.dao.model.custom;

/**
 * 
 * @author WSO2Telco
 *
 */

public class ListProvisionedRequestWrapperDTO extends RequestDTO {

	private static final long serialVersionUID = -1776869932314973555L;

	/** The msisdn param **/
	private String msisdn;

	/** The offset param **/
	private String offSet;

	/** The limit param **/
	private String limit;

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn
	 *            to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the offSet
	 */
	public String getOffSet() {
		return offSet;
	}

	/**
	 * @param offSet
	 *            to set
	 */
	public void setOffSet(String offSet) {
		this.offSet = offSet;
	}

	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

}
