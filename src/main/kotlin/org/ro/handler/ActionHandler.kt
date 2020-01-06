package org.ro.handler

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import org.ro.core.aggregator.ActionAggregator
import org.ro.to.Action
import org.ro.to.TransferObject

class ActionHandler : BaseHandler() {

    override fun doHandle() {
        logEntry.addAggregator(ActionAggregator())
        update()
    }

    @UnstableDefault
    override fun parse(response: String): TransferObject? {
        return Json.parse(Action.serializer(), response)
    }
}

