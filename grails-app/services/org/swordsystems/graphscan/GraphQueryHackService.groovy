package org.swordsystems.graphscan

import org.springframework.beans.factory.InitializingBean

class GraphQueryHackService implements InitializingBean {
	def grailsApplication

	static transactional = false

	@Delegate
	FacebookAccessor fah = new FacebookAccessor()

	String fieldNames = 'id,name,description,caption,from,to,application'
	int pageSize = 250
	String scanUserQueueName
	String processFeedGraphQueueName
	String retryFeedEntryQueueName
	String errorQueueName

	@Override
	void afterPropertiesSet() {
		def config = grailsApplication?.config
		facebookHost = config.facebook.host
		accessToken = config.facebook.accessToken
	}

	def queueUserToScan(User u, depth = 2) {
		if (u.friends && u.friends.size() > 0) return

		def r = scanFriends(u.fbId)
		depth--
		def friends = r.collect {userJsonPersister(it)}.findAll{it != null}
		log.info("Persisted friends: ${friends?.size()}")
		if (friends) {
			if (u.friends)
				u.friends.addAll(friends)
			else
				u.friends = friends
			if (!u.save(flush: true)) {
				log.error("Could not save friends $u.fbId: ${u.retrieveErrors()}")
			}

			if (depth > 0) {
				friends.each{queueUserToScan(it, depth)}
			}
		}
	}

	def scanFriends(userId) {
		log.info("Scanning friends for user: $userId")
		def params = [limit:pageSize]

		def result = getResource("${userId}/friends", params)
		if (result == null) {
			return []
		}
		log.info("Friend list for user $userId has ${result.data.size()} entries")
		//TODO - push friend relation to some other flow.  will only work once nodes are persisted
		result.data
	}

	Random random = new Random()
	def addUsers(count) {
		def min = (Calendar.instance.timeInMillis) % 1000000 * 1000
		min = 1000000

		def r = (min..(min+count))
		r.each {n ->
			def name = "User $n"
			def u = new User(fbId:n, name:name)
			if (!u.save(flush: true)) {
				log.error("Could not save user $id: ${u.retrieveErrors()}")
				u = null
			}
		}
	}

	def userJsonPersister(user) {

		def id = user.id as long
		def u2 = User.findByFbId(id)
		if (u2) return u2

		User u = new User(name:user.name, fbId:user.id)
		if (!u.save(flush: true)) {
			log.error("Could not save user $id: ${u.retrieveErrors()}")
			u = null
		}
		return u
	}

}
