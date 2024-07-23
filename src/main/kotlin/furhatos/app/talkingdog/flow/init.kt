package furhatos.app.talkingdog.flow

import furhatos.app.talkingdog.flow.main.Idle
import furhatos.app.talkingdog.flow.main.Greeting
import furhatos.app.talkingdog.setting.DISTANCE_TO_ENGAGE
import furhatos.app.talkingdog.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.State
import furhatos.app.talkingdog.flow.chatbot.serviceKey
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.AcapelaVoice
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.nlu.SimpleIntent

val Init: State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)

        /** Check API key for the OpenAI GPT3 language model has been set */
        if (serviceKey.isEmpty()) {
            println("Missing API key for OpenAI GPT3 language model. ")
            exit()
        }

        /** Set the Persona */
        furhat.voice = AcapelaVoice("WillLittleCreature", rate=1.3)
        furhat.character = "default"

        /** start the interaction */
        goto(InitFlow)
    }

}

val InitFlow: State = state() {
    onEntry {
        when {
            users.hasAny() -> goto(Greeting)
            !users.hasAny() -> goto(Idle)
        }
    }

}
