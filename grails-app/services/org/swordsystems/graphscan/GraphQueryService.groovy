package org.swordsystems.graphscan

import org.springframework.beans.factory.InitializingBean

class GraphQueryService implements InitializingBean {
	def grailsApplication
	def facebookAppService

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

		scanUserQueueName = config.routes.scanUser.queueName
		processFeedGraphQueueName = config.routes.processFeedGraph.queueName
		errorQueueName = config.routes.errors.queueName
		retryFeedEntryQueueName = config.routes.retryFeedEntry.queueName
	}

	def queueUserToScan(userId, depth = 1) {

	}

	def scanFriends(userId) {
		log.info("Scanning friends for user: $userId")
		def params = [limit:pageSize]

		def result = getResource("${userId}/friends", params)
		if (result == null) {
			return
		}
		log.info("Friend list for user $userId has ${result.data.size()} entries")
		//TODO - push friend relation to some other flow.  will only work once nodes are persisted
		result.data.list()
	}

	def userJsonPersister(data) {
		def user = data.user

		def id = user.id as long
		if (User.exists(id)) return

		User u = new User(name:name)
		u.id = id
		if (!u.save()) {
			log.error("Could not save user $id: ${u.retrieveErrors()}")
		}
	}

	def userDepthCheck(data) {
		def depth = data.depth
		if (depth <= 0) return
		queueUserToScan(data.user.id, depth)
	}

}
