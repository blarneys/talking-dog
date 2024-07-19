package furhatos.app.talkingdog.flow.chatbot

import furhatos.flow.kotlin.*
import furhatos.app.talkingdog.flow.*
import furhatos.app.talkingdog.gestures.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val MainChat = state(Parent) {
    onEntry {
//        activate(currentPersona)
        delay(2000)
        Furhat.dialogHistory.clear()
        furhat.say("Hi, I'm Bouncer, a pug who can speak English.")
        reentry()
    }

    onReentry {
        random({
            furhat.gesture(sniffing1, async = true)
        }, {
            furhat.gesture(sniffing3, async = true)
        }, {
            furhat.gesture(panting1, async = true)
        }, policy = RandomPolicy.DECK_RESHUFFLE_NO_REPEAT)
        furhat.listen()
    }

    onResponse("can we stop", "goodbye") {
        furhat.say ("Alright, goodbye, thanks for stopping to chat.")
    }

    onResponse {
//        furhat.gesture(GazeAversion(2.0))

        val response = call {
            OpenAIChatbot("The following is a conversation between Bouncer, the silly, British dog " +
                    "who was born with the ability to speak English, and a Person. Bouncer speaks simply, and only " +
                    "knows things that dogs know about.",
                "Person", "Bouncer")
                .getNextResponse()
        } as String
        furhat.say(response)
        random({
            furhat.gesture(bark1, async = true)
        }, {
            furhat.gesture(bark2, async = true)
        }, {
            furhat.gesture(bark3, async = true)
        }, {}, {}, policy = RandomPolicy.DECK_RESHUFFLE_NO_REPEAT)
        reentry()
    }

    onNoResponse {
        reentry()
    }
}