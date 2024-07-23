package furhatos.app.talkingdog.flow.main

import furhatos.app.talkingdog.flow.Parent
import furhatos.app.talkingdog.flow.chatbot.MainChat
import furhatos.flow.kotlin.*
import furhatos.gestures.*

import furhatos.records.Location

val Greeting: State = state(Parent) {
    onEntry {

        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        goto(MainChat)
    }
}

