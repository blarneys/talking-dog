package furhatos.app.talkingdog.flow.main

import furhatos.app.talkingdog.flow.askForAnything
import furhatos.app.talkingdog.flow.Parent
import furhatos.app.talkingdog.flow.chatbot.MainChat
import furhatos.flow.kotlin.*

import furhatos.records.Location

val Greeting: State = state(Parent) {
    onEntry {
        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        askForAnything("Hi there, anything you'd like to ask a talking dog?")
        goto(MainChat)
    }
}

