package furhatos.app.talkingdog.flow.chatbot

import furhatos.flow.kotlin.*
import furhatos.app.talkingdog.flow.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val MainChat = state(Parent) {
    onEntry {
//        activate(currentPersona)
        delay(2000)
        Furhat.dialogHistory.clear()
        furhat.say("Hi, I'm Bouncer, a dog who can speak English.")
        reentry()
    }

    onReentry {
        furhat.listen()
    }

    onResponse("can we stop", "goodbye") {
        furhat.say ("Alright, goodbye, thanks for stopping to chat.")
    }

    onResponse {
        furhat.gesture(GazeAversion(2.0))
        val response = call {
            OpenAIChatbot("The following is a conversation between Bouncer, the enthusiastic, British dog " +
                    "who was born with the ability to speak English, and a Person.", "Person", "Bouncer")
                .getNextResponse()
        } as String
        furhat.say(response)
        reentry()
    }

    onNoResponse {
        reentry()
    }
}