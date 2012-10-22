package org.swordsystems.graphscan

class User {
	String name
	Long fbId

	static hasMany = [friends: User, items:FeedItem]
	static mappedBy = [items:'owner']

	static constraints = {
		fbId unique: true
	}

	static mapping = {
		//id generator:'assigned'
	}
}
