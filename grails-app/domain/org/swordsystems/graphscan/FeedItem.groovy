package org.swordsystems.graphscan

class FeedItem {

	User owner
	String name
	User fromUser
	User toUser
	FacebookApp app
	Date itemDate

	static mapping = {
		id generator:'assigned'
	}

	static constraints = {
		name nullable:true
		fromUser nullable:true
		toUser nullable:true
		app nullable:true
	}
}
