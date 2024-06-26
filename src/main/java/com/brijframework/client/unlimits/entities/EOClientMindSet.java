/**
 * 
 */
package com.brijframework.client.unlimits.entities;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOCustObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "EOCLIENT_MINDSET")
public class EOClientMindSet extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name ="MIND_SET_ID")
	private Long mindSetId;

	@JoinColumn(name = CUST_BUSINESS_APP_ID, nullable = false)
	@ManyToOne
	private EOCustBusinessApp custBusinessApp;

	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}
	
}
