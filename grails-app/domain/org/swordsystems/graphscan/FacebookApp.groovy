package org.swordsystems.graphscan

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

@ToString(includes="name",includeNames=false)
@EqualsAndHashCode
class FacebookApp {

	String name
	String category

	static mapping = {
		id generator:'assigned'
	}

	static constraints = {
		name nullable:true
		category nullable:true
	}
}
